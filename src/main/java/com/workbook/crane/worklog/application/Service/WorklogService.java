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

  private final String sender = "?????????";

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
      if (partnerOptional.isEmpty()) {
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
    if (worklog == null) {
      throw new Exception("Worklog not found");
    }

    worklog.markWorklogAsDeleted();

    return WorklogInfo.from(worklog);
  }

  public void sendWorklogEmail(WorklogExcelDto dto) throws Exception {
    log.info(System.getProperty("java.io.tmpdir"));

    //    String filePath = System.getProperty("java.io.tmpdir") + "/worklog" + Instant.now().toEpochMilli() + ".xlsx";
    String filePath =
        System.getProperty("java.io.tmpdir") + "/worklog" + Instant.now().toEpochMilli() + ".xlsx";
    extractWorklog(dto, filePath);
    sendEmail(dto.getEmail(), filePath);
  }

  private void extractWorklog(WorklogExcelDto dto, String filePath) throws Exception {
    User user = authService.getUserOrElseThrow(dto.getUsername());
    List<Worklog> worklogList =
        worklogRepository.findWorklogInGivenPeriod(dto.getFrom(), dto.getTo(), user);
    Double totalWorkPay = worklogList.stream().mapToDouble(Worklog::getWorkPay).sum();

    try {
      XSSFRow xssfRow;
      XSSFCell xssfCell;

      int rowNo = 0; // ?????? ??????
      XSSFWorkbook xssfWb = new XSSFWorkbook(); //XSSFWorkbook ?????? ??????
      XSSFSheet xssfSheet = xssfWb.createSheet("?????? ??????1"); // ???????????? ?????? ??????
      // ?????? ?????????
      XSSFFont font = xssfWb.createFont();
      font.setFontName(HSSFFont.FONT_ARIAL); // ?????? ?????????
      font.setFontHeightInPoints((short) 20); // ?????? ??????
      font.setBold(true); // Bold ??????
      font.setColor(new XSSFColor(Color.decode("#457ba2"))); // ?????? ??? ??????
      // ????????? ??? ?????????
      CellStyle cellStyle = xssfWb.createCellStyle();
      xssfSheet.setColumnWidth(0, 2100); // ?????? cell ?????? => 5??????(e) cell 2100=7.63
      xssfSheet.setColumnWidth(1, 3000); // 7??????(h) cell 3400=12.63
      xssfSheet.setColumnWidth(2, 5000); // 7??????(h) cell 3400=12.63
      xssfSheet.setColumnWidth(3, 3000); // 7??????(h) cell 3400=12.63
      xssfSheet.setColumnWidth(4, 5000); // 7??????(h) cell 3400=12.63
      xssfSheet.setColumnWidth(5, 4000); // 7??????(h) cell 3400=12.63
      cellStyle.setFont(font); // cellStyle??? font??? ??????
      cellStyle.setAlignment(HorizontalAlignment.CENTER); // ??????
      cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
      // ?????????
      xssfSheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 5)); //??????, ????????????, ??????, ???????????? ??????
      // ????????? ??????
      xssfRow = xssfSheet.createRow(rowNo++); // ??? ?????? ??????
      xssfCell = xssfRow.createCell((short) 0); // ????????? ?????? ??? ?????? ??????
      xssfCell.setCellStyle(cellStyle); // ?????? ????????? ??????
      xssfCell.setCellValue("????????????"); // ????????? ??????
      xssfRow = xssfSheet.createRow(rowNo++);

      // ????????????
      XSSFFont totalPayFont = xssfWb.createFont();
      font.setFontName(HSSFFont.FONT_ARIAL); // ?????? ?????????
      font.setBold(true); // Bold ??????
      CellStyle totalPayCellStyle = xssfWb.createCellStyle();
      totalPayCellStyle.setFont(totalPayFont);

      xssfSheet.addMergedRegion(new CellRangeAddress(2, 2, 1, 5)); //??????, ????????????, ??????, ???????????? ??????
      xssfRow = xssfSheet.createRow(rowNo++);
      xssfCell = xssfRow.createCell((short) 0);
      xssfCell.setCellStyle(totalPayCellStyle);
      xssfCell.setCellValue("??????");

      xssfCell = xssfRow.createCell((short) 1);
      xssfCell.setCellStyle(totalPayCellStyle);
      xssfCell.setCellValue(workPayFormat.format(totalWorkPay));

      xssfRow = xssfSheet.createRow(rowNo++); // ?????? ?????? //????????? ????????? ??????

      CellStyle tableCellStyle = xssfWb.createCellStyle();
      tableCellStyle.setBorderTop(BorderStyle.THIN); // ????????? ??????
      tableCellStyle.setBorderBottom(BorderStyle.THIN); // ????????? ?????????
      tableCellStyle.setBorderLeft(BorderStyle.THIN); // ????????? ??????
      tableCellStyle.setBorderRight(BorderStyle.THIN); // ????????? ?????????

      xssfRow = xssfSheet.createRow(rowNo++);
      xssfCell = xssfRow.createCell((short) 0);
      xssfCell.setCellStyle(tableCellStyle);
      xssfCell.setCellValue("seq");

      xssfCell = xssfRow.createCell((short) 1);
      xssfCell.setCellStyle(tableCellStyle);
      xssfCell.setCellValue("????????????");

      xssfCell = xssfRow.createCell((short) 2);
      xssfCell.setCellStyle(tableCellStyle);
      xssfCell.setCellValue("????????????");

      xssfCell = xssfRow.createCell((short) 3);
      xssfCell.setCellStyle(tableCellStyle);
      xssfCell.setCellValue("????????????");

      xssfCell = xssfRow.createCell((short) 4);
      xssfCell.setCellStyle(tableCellStyle);
      xssfCell.setCellValue("??????");

      xssfCell = xssfRow.createCell((short) 5);
      xssfCell.setCellStyle(tableCellStyle);
      xssfCell.setCellValue("??????");

      xssfCell = xssfRow.createCell((short) 6);
      xssfCell.setCellStyle(tableCellStyle);
      xssfCell.setCellValue("?????????");

      int seq = 1;

      for (Worklog worklog : worklogList) {
        xssfRow = xssfSheet.createRow(rowNo++);

        xssfCell = xssfRow.createCell((short) 0);
        xssfCell.setCellStyle(tableCellStyle);
        xssfCell.setCellValue(seq++);

        xssfCell = xssfRow.createCell((short) 1);
        xssfCell.setCellStyle(tableCellStyle);
        xssfCell.setCellValue(worklog.getWorkDate().toString());

        xssfCell = xssfRow.createCell((short) 2);
        xssfCell.setCellStyle(tableCellStyle);
        xssfCell.setCellValue(worklog.getWorkTime().getDescription());

        xssfCell = xssfRow.createCell((short) 3);
        xssfCell.setCellStyle(tableCellStyle);
        xssfCell.setCellValue(worklog.getLocation());

        HeavyEquipment heavyEquipment = heavyEquipmentRepository.getOne(
            worklog.getEquipment().getId());

        xssfCell = xssfRow.createCell((short) 4);
        xssfCell.setCellStyle(tableCellStyle);
        xssfCell.setCellValue(heavyEquipment.getEquipment());

        xssfCell = xssfRow.createCell((short) 5);
        xssfCell.setCellStyle(tableCellStyle);
        xssfCell.setCellValue(workPayFormat.format(worklog.getWorkPay()));

        xssfCell = xssfRow.createCell((short) 6);
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
    String subject = "[?????????] ?????? ?????? ??????";
    //?????????
    String[] recipients = {email};
    //?????????
    String[] references = {email};
    //????????????
    String[] attachedFiles = {};
    if (StringUtils.isNotEmpty(filePath)) {
      attachedFiles = new String[]{filePath};
    }

    //?????? ?????? ??????
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
      //?????? ??????  ?????? ?????? ??????
      Authenticator auth = new Authenticator() {
        protected PasswordAuthentication getPasswordAuthentication() {
          return new PasswordAuthentication(emailId, password);
        }
      };

      //?????? ?????? ??????
      Session session = Session.getInstance(props, auth);

      //MIME ?????? ??????
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

      //?????? ???/?????? ?????? ??????
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

        //????????? ????????? ?????????
        sb.append("<h4>???????????????.</h4>");
        sb.append("<h4>???????????? ???????????? ???????????????. ??????????????? ???????????? ????????? ????????????.</h4>");
        sb.append("<h4>??????????????????..</h4>");
        String sendText = sb.toString();

        MimeBodyPart mTextPart = new MimeBodyPart();
        mTextPart.setText(sendText, "UTF-8", "html");
        mp.addBodyPart(mTextPart);

        //?????? ??????????????? ?????? ??? ??? ??????
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
          FileDataSource fds = new FileDataSource(attachedFiles[i]); //?????? ????????????
          mbp.setDataHandler(new DataHandler(fds));
          mbp.setFileName(fds.getName());
          mp.addBodyPart(mbp);
        }

        message.setContent(mp);

        //?????????
        message.setRecipients(RecipientType.TO, InternetAddress.parse(recipients[i], false));
        //?????????
        message.setRecipients(RecipientType.CC, InternetAddress.parse(references[i], false));
        Transport.send(message);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
