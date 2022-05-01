package com.workbook.crane.worklog.application.Service;

import com.workbook.crane.partner.application.service.PartnerService;
import com.workbook.crane.partner.domain.model.Partner;
import com.workbook.crane.user.application.service.AuthService;
import com.workbook.crane.user.domain.model.User;
import com.workbook.crane.worklog.application.Dto.WorklogExcelDto;
import com.workbook.crane.worklog.application.model.command.WorklogCreateCommand;
import com.workbook.crane.worklog.application.model.criteria.WorklogSearchCriteria;
import com.workbook.crane.worklog.application.model.info.WorklogCreateInfo;
import com.workbook.crane.worklog.application.model.info.WorklogInfo;
import com.workbook.crane.worklog.application.model.info.WorklogSearchAllInfo;
import com.workbook.crane.worklog.domain.model.HeavyEquipment;
import com.workbook.crane.worklog.domain.model.Worklog;
import com.workbook.crane.worklog.domain.repository.HeavyEquipmentRepository;
import com.workbook.crane.worklog.domain.repository.WorklogRepository;
import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import javax.activation.CommandMap;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.activation.MailcapCommandMap;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeMultipart;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorklogService {

  private static final DecimalFormat workPayFormat = new DecimalFormat("#,###.##");
  private final AuthService authService;
  private final PartnerService partnerService;
  private final HeavyEquipmentService heavyEquipmentService;
  private final WorklogRepository worklogRepository;
  private final HeavyEquipmentRepository heavyEquipmentRepository;

  @Value("${worklog.email.id}")
  private String emailId;

  @Value("${worklog.email.password}")
  private String password;

  private final String sender = "두루미";

  @Transactional
  public WorklogCreateInfo createWorklog(WorklogCreateCommand command) throws Exception {
    User user = authService.getUserOrElseThrow(command.getUsername());
    Partner partner = partnerService.getPartnerOrElseThrow(command.getPartnerId());
    HeavyEquipment heavyEquipment =
        heavyEquipmentService.getHeavyEquipmentOrElseThrow(command.getEquipmentId(), user);

    return WorklogCreateInfo.from(
        worklogRepository.save(Worklog.create(command, user, partner, heavyEquipment)));
  }

  @Transactional(readOnly = true)
  public WorklogSearchAllInfo searchAllWorklog(WorklogSearchCriteria criteria)
      throws Exception {
    User user = authService.getUserOrElseThrow(criteria.getUsername());
    Partner partner = null;

    if (StringUtils.isNotEmpty(criteria.getPartnerName())) {
      Optional<Partner> partnerOptional = partnerService.isExistingPartnerNameByUser(
          criteria.getPartnerName(), user.getId());
      if(partnerOptional.isEmpty()) {
        return WorklogSearchAllInfo.getDefault();
      }
      partner = partnerOptional.get();
    }

    Page<Worklog> pages = worklogRepository.findAllWorklogByCriteria(criteria, partner, user);
    return WorklogSearchAllInfo.from(pages);
  }

  @Transactional(readOnly = true)
  public WorklogInfo getWorklogById(Long id, String username) throws Exception {
    User user = authService.getUserOrElseThrow(username);
    return WorklogInfo.from(worklogRepository.findByIdAndUser(id, user));
  }

  @Transactional
  public WorklogInfo deleteWorklog(Long id, String username) throws Exception {
    User user = authService.getUserOrElseThrow(username);
    Worklog worklog = worklogRepository.findByIdAndUser(id, user);
    if(worklog == null){
      throw new Exception("Worklog not found");
    }

    worklog.markWorklogAsDeleted();

    return WorklogInfo.from(worklog);
  }

  public void sendWorklogEmail(WorklogExcelDto dto) throws Exception {
    log.info(System.getProperty("java.io.tmpdir"));

    String filePath = System.getProperty("java.io.tmpdir") + "/worklog" + Instant.now().toEpochMilli() + ".xlsx";
    extractWorklog(dto, filePath);
    sendEmail(dto.getEmail(), filePath);
  }

  private void extractWorklog (WorklogExcelDto dto, String filePath) throws Exception{
    User user = authService.getUserOrElseThrow(dto.getUsername());
    List<Worklog> worklogList =
        worklogRepository.findWorklogInGivenPeriod(dto.getFrom(), dto.getTo(), user);
    Double totalWorkPay = worklogList.stream().mapToDouble(Worklog::getWorkPay).sum();

    try {
      XSSFRow xssfRow;
      XSSFCell xssfCell;

      int rowNo = 0; // 행의 갯수
      XSSFWorkbook xssfWb = new XSSFWorkbook(); //XSSFWorkbook 객체 생성
      XSSFSheet xssfSheet = xssfWb.createSheet("워크 시트1"); // 워크시트 이름 설정
      // 폰트 스타일
      XSSFFont font = xssfWb.createFont();
      font.setFontName(HSSFFont.FONT_ARIAL); // 폰트 스타일
      font.setFontHeightInPoints((short) 20); // 폰트 크기
      font.setBold(true); // Bold 설정
      font.setColor(new XSSFColor(Color.decode("#457ba2"))); // 폰트 색 지정
      // 테이블 셀 스타일
      CellStyle cellStyle = xssfWb.createCellStyle();
      xssfSheet.setColumnWidth(0, 2100); // 특정 cell 설정 => 5번째(e) cell 2100=7.63
      xssfSheet.setColumnWidth(1, 3000); // 7번째(h) cell 3400=12.63
      xssfSheet.setColumnWidth(2, 5000); // 7번째(h) cell 3400=12.63
      xssfSheet.setColumnWidth(3, 3000); // 7번째(h) cell 3400=12.63
      xssfSheet.setColumnWidth(4, 5000); // 7번째(h) cell 3400=12.63
      xssfSheet.setColumnWidth(5, 4000); // 7번째(h) cell 3400=12.63
      cellStyle.setFont(font); // cellStyle에 font를 적용
      cellStyle.setAlignment(HorizontalAlignment.CENTER); // 정렬
      cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
      // 셀병합
      xssfSheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 5)); //첫행, 마지막행, 첫열, 마지막열 병합
      // 타이틀 생성
      xssfRow = xssfSheet.createRow(rowNo++); // 행 객체 추가
      xssfCell = xssfRow.createCell((short) 0); // 추가한 행에 셀 객체 추가
      xssfCell.setCellStyle(cellStyle); // 셀에 스타일 지정
      xssfCell.setCellValue("근무일정"); // 데이터 입력
      xssfRow = xssfSheet.createRow(rowNo++);

      // 총합추가
      XSSFFont totalPayFont = xssfWb.createFont();
      font.setFontName(HSSFFont.FONT_ARIAL); // 폰트 스타일
      font.setBold(true); // Bold 설정
      CellStyle totalPayCellStyle = xssfWb.createCellStyle();
      totalPayCellStyle.setFont(totalPayFont);

      xssfSheet.addMergedRegion(new CellRangeAddress(2, 2, 1, 5)); //첫행, 마지막행, 첫열, 마지막열 병합
      xssfRow = xssfSheet.createRow(rowNo++);
      xssfCell = xssfRow.createCell((short) 0);
      xssfCell.setCellStyle(totalPayCellStyle);
      xssfCell.setCellValue("총합");

      xssfCell = xssfRow.createCell((short) 1);
      xssfCell.setCellStyle(totalPayCellStyle);
      xssfCell.setCellValue(workPayFormat.format(totalWorkPay));

      xssfRow = xssfSheet.createRow(rowNo++); // 빈행 추가 //테이블 스타일 설정

      CellStyle tableCellStyle = xssfWb.createCellStyle();
      tableCellStyle.setBorderTop(BorderStyle.THIN); // 테두리 위쪽
      tableCellStyle.setBorderBottom(BorderStyle.THIN); // 테두리 아래쪽
      tableCellStyle.setBorderLeft(BorderStyle.THIN); // 테두리 왼쪽
      tableCellStyle.setBorderRight(BorderStyle.THIN); // 테두리 오른쪽

      xssfRow = xssfSheet.createRow(rowNo++);
      xssfCell = xssfRow.createCell((short) 0);
      xssfCell.setCellStyle(tableCellStyle);
      xssfCell.setCellValue("seq");

      xssfCell = xssfRow.createCell((short) 1);
      xssfCell.setCellStyle(tableCellStyle);
      xssfCell.setCellValue("근무시간");

      xssfCell = xssfRow.createCell((short) 2);
      xssfCell.setCellStyle(tableCellStyle);
      xssfCell.setCellValue("근무장소");

      xssfCell = xssfRow.createCell((short) 3);
      xssfCell.setCellStyle(tableCellStyle);
      xssfCell.setCellValue("장비");

      xssfCell = xssfRow.createCell((short) 4);
      xssfCell.setCellStyle(tableCellStyle);
      xssfCell.setCellValue("금액");

      xssfCell = xssfRow.createCell((short) 5);
      xssfCell.setCellStyle(tableCellStyle);
      xssfCell.setCellValue("거래처");


      int seq = 1;

      for (Worklog worklog : worklogList) {
        xssfRow = xssfSheet.createRow(rowNo++);

        xssfCell = xssfRow.createCell((short) 0);
        xssfCell.setCellStyle(tableCellStyle);
        xssfCell.setCellValue(seq++);

        xssfCell = xssfRow.createCell((short) 1);
        xssfCell.setCellStyle(tableCellStyle);
        xssfCell.setCellValue(worklog.getWorkTime().getDescription());

        xssfCell = xssfRow.createCell((short) 2);
        xssfCell.setCellStyle(tableCellStyle);
        xssfCell.setCellValue(worklog.getLocation());

        HeavyEquipment heavyEquipment = heavyEquipmentRepository.getOne(worklog.getEquipment().getId());

        xssfCell = xssfRow.createCell((short) 3);
        xssfCell.setCellStyle(tableCellStyle);
        xssfCell.setCellValue(heavyEquipment.getEquipment());

        xssfCell = xssfRow.createCell((short) 4);
        xssfCell.setCellStyle(tableCellStyle);
        xssfCell.setCellValue(workPayFormat.format(worklog.getWorkPay()));

        xssfCell = xssfRow.createCell((short) 5);
        xssfCell.setCellStyle(tableCellStyle);
        xssfCell.setCellValue(worklog.getPartner().getCompanyName());
      }



      File file = new File(filePath);
      FileOutputStream fos = null;
      fos = new FileOutputStream(file);
      xssfWb.write(fos);
      if (fos != null) {
        fos.close();
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }


  private void sendEmail(String email, String filePath) {
    String subject = "[두루미] 근무 일정 엑셀";
    //수신인
    String[] recipients = {email};
    //참조인
    String[] references = {email};
    //첨부파일
    String[] attachedFiles = {};
    if (StringUtils.isNotEmpty(filePath)) {
      attachedFiles = new String[]{filePath};
    }

    //메일 옵션 설정
    Properties props = new Properties();
    props.put("mail.transport.protocol", "smtp");
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.port", "465");
    props.put("mail.smtp.auth", "true");

    props.put("mail.smtp.starttls.required", "true");
    props.put("mail.smtp.ssl.protocols", "TLSv1.2");

    props.put("mail.smtp.quitwait", "false");
    props.put("mail.smtp.socketFactory.port", "465");
    props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    props.put("mail.smtp.socketFactory.fallback", "false");

    try {
      //메일 서버  인증 계정 설정
      Authenticator auth = new Authenticator() {
        protected PasswordAuthentication getPasswordAuthentication() {
          return new PasswordAuthentication(emailId, password);
        }
      };

      //메일 세션 생성
      Session session = Session.getInstance(props, auth);

      //MIME 타입 설정
      MailcapCommandMap MailcapCmdMap = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
      MailcapCmdMap.addMailcap(
          "text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
      MailcapCmdMap.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
      MailcapCmdMap.addMailcap(
          "text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
      MailcapCmdMap.addMailcap(
          "multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
      MailcapCmdMap.addMailcap(
          "message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
      CommandMap.setDefaultCommandMap(MailcapCmdMap);

      //메일 송/수신 옵션 설정
      Message message = new MimeMessage(session);
      message.setFrom(new InternetAddress(emailId, sender));
      message.setSubject(subject);
      message.setSentDate(new Date());

      MimeBodyPart mbp;
      Multipart mp;
      StringBuilder sb = new StringBuilder();

      for (int i = 0; i < references.length; i++) {
        mbp = new MimeBodyPart();
        mp = new MimeMultipart();
        sb.setLength(0);

        //메일에 출력할 텍스트
        sb.append("<h4>안녕하세요.</h4>");
        sb.append("<h4>요청하신 근무일정 엑셀입니다. 첨부파일을 확인하여 주시기 바랍니다.</h4>");
        sb.append("<h4>두루미로부터..</h4>");
        String sendText = sb.toString();

        MimeBodyPart mTextPart = new MimeBodyPart();
        mTextPart.setText(sendText, "UTF-8", "html");
        mp.addBodyPart(mTextPart);

        //보낼 첨부파일이 여러 개 일 경우
        if (attachedFiles[i].contains(",")) {
          String[] attachedFiles2 = attachedFiles[i].split(",");
          for (int j = 0; j < attachedFiles2.length; j++) {
            FileDataSource fds = new FileDataSource(attachedFiles2[j]);
            mbp = new MimeBodyPart();
            mbp.setDataHandler(new DataHandler(fds));
            mbp.setFileName(fds.getName());
            mp.addBodyPart(mbp);
          }
        } else {
          FileDataSource fds = new FileDataSource(attachedFiles[i]); //파일 읽어오기
          mbp.setDataHandler(new DataHandler(fds));
          mbp.setFileName(fds.getName());
          mp.addBodyPart(mbp);
        }

        message.setContent(mp);

        //수신인
        message.setRecipients(RecipientType.TO, InternetAddress.parse(recipients[i], false));
        //참조인
        message.setRecipients(RecipientType.CC, InternetAddress.parse(references[i], false));
        Transport.send(message);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
