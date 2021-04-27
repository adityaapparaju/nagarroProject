package nagarro.nagarroproject.web;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import nagarro.nagarroproject.model.Statement;
import nagarro.nagarroproject.service.StatementService;
import nagarro.nagarroproject.web.dto.StatementDto;


@Controller
public class StatementController {

    @Autowired
    private StatementService statementService;
   
    private static final Logger log = LoggerFactory.getLogger(StatementController.class);
   
    private static final String MESSAGE_ATTRIBUTE = "message";
   
SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }
   
 
   
    @ModelAttribute("statement")
    public StatementDto statementDto() {
        return new StatementDto();
    }
   
   

   
   
    @GetMapping("/main")
    public String mainForm(Model model) {
        model.addAttribute("statement", new StatementDto());

        return "main";
    }
   
   
   
   
    @PostMapping(value = "/main")
    public String main(Model model, @ModelAttribute("statement") @Valid StatementDto statementDto) {

    log.info("view statement - start");
   
   
    List<Statement> statementListThreeMonths = new ArrayList<>();
    List<Statement> statementListFromToAmount = new ArrayList<>();
    List<Statement> statementListFromToDate = new ArrayList<>();
    List<Statement> statementListAllDetails = new ArrayList<>();
    List<Statement> statementListByAccId = new ArrayList<>();
    List<Statement> statementListFinal = new ArrayList<>();
    String message = "success";
    try {
    if (statementDto.isValidAccount())
    {
       statementListByAccId =  statementService.findAllByAccountId(statementDto.getAccountId());
    }
    List<Statement> statementList =  statementService.findAll();
       
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    if ( isAdmin(auth))
    {
    if ( isInvalidParameter( statementDto))
    {
       log.info("invalid parameter provided");
       message = "Invalid parameter" ;
    }
    else if (statementDto.getAccountId() != null && statementDto.getAccountId() >=0)
    {
    statementListFinal = setValidDetails(statementListByAccId,statementDto, statementListFromToAmount,statementListFromToDate,
    statementListAllDetails);
    }
   
    else
    {
    message= "please enter account id";
  }
   
   
    }
    else if (isUser(auth))
    {
    if (isNoParameterUser( statementDto,  auth))
    {
        log.info("no parameter provided");

        log.info("calculating three months back statement");
       
   
    setThreeMonthStatementList(statementList, statementListThreeMonths);
   
    String stListThreeMonths = String.format("statement list of three months  %s", statementListThreeMonths);
            log.info(stListThreeMonths);
           statementListFinal = statementListThreeMonths;
    }
    else
    {
    message = "insufficient privileges";
    }
    }
    else if (isUserTest(auth))
    {
    if (isAnyParameter(statementDto))
    {
    throw new Exception(HttpStatus.UNAUTHORIZED + " : unauthorized to access");
    }
    }
       model.addAttribute("statementList",statementListFinal);
 
    model.addAttribute(MESSAGE_ATTRIBUTE,message );
    log.info("view statement - end");
   
    }
   
    catch(ParseException e)
    {
    log.error(String.format("Invalid date format %s", e));
    model.addAttribute(MESSAGE_ATTRIBUTE,e.getMessage() );
    return "main";
    }
    catch(Exception e)
    {
    log.error(String.format(" details %s", e.getMessage()));

    model.addAttribute(MESSAGE_ATTRIBUTE,e.getMessage() );
    return "main";
    }

        return "main";

    }
   

   
   
    private List<Statement> setValidDetails(List<Statement> statementListByAccId, @Valid StatementDto statementDto,
List<Statement> statementListFromToAmount, List<Statement> statementListFromToDate,
List<Statement> statementListAllDetails) throws ParseException {
    List<Statement> statementListFinal=new ArrayList<>();
    if (isAccountId( statementDto)){

            statementListFinal = setStatementAccountId(statementListByAccId, statementDto );
    }
   
else if (isFromToAmount( statementDto) ){

           
            statementListFinal = setStatementFromToAmount(statementListByAccId, statementDto,statementListFromToAmount );

}



else if (isFromToDate(statementDto))
{

            statementListFinal = setStatementFromToDate(statementListByAccId, statementDto,statementListFromToDate );

}

else if (isAllDetails(statementDto)){
           
statementListFinal = setStatementAllDetails(statementListByAccId,statementDto,statementListAllDetails);

}
    return statementListFinal;

}



@GetMapping("/")
    public String root() {
        return "login";
    }
   
    public boolean isFromToAmount(StatementDto statementDto) {
    return statementDto.getFromAmount() != null && statementDto.getToAmount() != null
&& StringUtils.isEmpty(statementDto.getFromDate()) && StringUtils.isEmpty(statementDto.getToDate());
   
    }
   
    public boolean isFromToDate(StatementDto statementDto) {
    return statementDto.getFromAmount() == null && statementDto.getToAmount() == null
&& !StringUtils.isEmpty(statementDto.getFromDate()) && !StringUtils.isEmpty(statementDto.getToDate());
    }

    public boolean isAllDetails(StatementDto statementDto)
    {
    return statementDto.getFromAmount() != null && statementDto.getToAmount() != null
&& !StringUtils.isEmpty(statementDto.getFromDate()) && !StringUtils.isEmpty(statementDto.getToDate());

    }

    public boolean isAccountId(StatementDto statementDto) {
    return statementDto.getFromAmount() == null && statementDto.getToAmount() == null
&& StringUtils.isEmpty(statementDto.getFromDate()) && StringUtils.isEmpty(statementDto.getToDate());

    }
   
    public boolean isAmountRange(StatementDto statementDto, Statement statement) {
    return statementDto.getFromAmount().compareTo(statement.getAmount()) <= 0 &&
statementDto.getToAmount().compareTo(statement.getAmount()) >= 0 ;
    }
   
    public boolean isInvalidParameter(StatementDto statementDto) throws ParseException
    {
    return (statementDto.getAccountId() != null && statementDto.getAccountId() < 0.0) ||
(statementDto.getFromAmount() != null && statementDto.getFromAmount().compareTo(BigDecimal.ZERO) < 0) ||
(statementDto.getToAmount() != null && statementDto.getToAmount().compareTo(BigDecimal.ZERO) < 0) ||
(statementDto.getFromAmount() != null && statementDto.getFromAmount().compareTo(BigDecimal.ZERO) >= 0 &&
statementDto.getToAmount() != null && statementDto.getToAmount().compareTo(BigDecimal.ZERO) >= 0 &&
statementDto.getFromAmount().compareTo(statementDto.getToAmount()) > 0.0) ||
(!StringUtils.isEmpty(statementDto.getFromDate())  && !StringUtils.isEmpty(statementDto.getToDate())
&& formatter.parse(statementDto.getFromDate()).compareTo(formatter.parse(statementDto.getToDate())) > 0);

    }
   
    public boolean isAllDetailsValid(StatementDto statementDto, Statement statement) throws ParseException
    {
    return formatter.parse(statementDto.getFromDate()).compareTo(formatter.parse(statement.getDateField())) <= 0 &&
    formatter.parse(statementDto.getToDate()).compareTo(formatter.parse(statement.getDateField())) >= 0 &&
    statementDto.getFromAmount().compareTo(statement.getAmount()) <= 0 && statementDto.getToAmount().compareTo(statement.getAmount())
    >= 0;
    }
   
    public boolean isFromToDateValid(StatementDto statementDto, Statement statement) throws ParseException
    {
    return formatter.parse(statementDto.getFromDate()).compareTo(formatter.parse(statement.getDateField())) <= 0 &&
    formatter.parse(statementDto.getToDate()).compareTo(formatter.parse(statement.getDateField())) >= 0;


    }
   
    public boolean isNoParameterUser(StatementDto statementDto, Authentication auth)
    {
    return statementDto.getAccountId()== null && statementDto.getFromAmount() == null && statementDto.getToAmount() == null
    && StringUtils.isEmpty(statementDto.getFromDate()) && StringUtils.isEmpty(statementDto.getToDate()) && auth != null ;

 
    }
   
   
    public boolean isAdmin(Authentication auth)
    {
    return auth != null && auth.getAuthorities() != null && !auth.getAuthorities().isEmpty() &&
    auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("User1"));
    }
    public boolean isUser(Authentication auth)
    {
    return auth != null && auth.getAuthorities() != null && !auth.getAuthorities().isEmpty() &&
    auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("User2"));
    }
    public boolean isUserTest(Authentication auth)
    {
    return auth != null && auth.getAuthorities() != null && !auth.getAuthorities().isEmpty() &&
    auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("UserTest"));
    }
   
   
    public void setlistThreeMonthStatement(int currentTime, int fromTime, List<Statement> statementListThreeMonths,
    Statement statement)
    {
    if (currentTime - fromTime <= 3)
    {
    statementListThreeMonths.add(statement);
    }
    }
   
public void setThreeMonthStatementList(List<Statement>statementList, List<Statement>statementListThreeMonths) throws ParseException
{
   Date currentDate = new Date();
for (Statement statement : statementList)
{
Date fromDate=formatter.parse(statement.getDateField());
int fromTime = fromDate.getYear() * 12 + fromDate.getMonth();
   int currentTime = currentDate.getYear() * 12 + currentDate.getMonth();
       
   setlistThreeMonthStatement( currentTime,  fromTime, statementListThreeMonths, statement);
}
}

public List<Statement> setStatementAllDetails(List<Statement>statementListByAccId, StatementDto statementDto,
List<Statement> statementListAllDetails) throws ParseException
{
log.info(" all details given");

       
for (Statement statement : statementListByAccId)
{
   
if (isAllDetailsValid( statementDto,  statement))
{
    statementListAllDetails.add(statement);
}
}
String stListAllDetails = String.format("statement list of all details  %s", statementListAllDetails);
    log.info(stListAllDetails);
return statementListAllDetails;

}

public List<Statement> setStatementFromToDate(List<Statement>statementListByAccId, StatementDto statementDto,
List<Statement> statementListFromToDate ) throws ParseException
{
String statementFromToDate = String.format("account id %s, from date %s, to date %s", statementDto.getAccountId(),
        statementDto.getFromDate(),statementDto.getToDate());
        log.info(statementFromToDate);
for (Statement statement : statementListByAccId)
{
   
   if (isFromToDateValid( statementDto, statement))
{
statementListFromToDate.add(statement);
}
}
String stListFromToDate = String.format("statement list from and to date  %s", statementListFromToDate);
    log.info(stListFromToDate);
        return statementListFromToDate;
}

public List<Statement> setStatementFromToAmount(List<Statement>statementListByAccId, StatementDto statementDto,
List<Statement> statementListFromToAmount ) throws ParseException
{
String statementFromToAmount = String.format("account id %s, from amount %s, to amount %s", statementDto.getAccountId(),
        statementDto.getFromAmount(),statementDto.getToAmount());
         log.info(statementFromToAmount);
for (Statement statement : statementListByAccId)
{
if (isAmountRange( statementDto,  statement))

{
statementListFromToAmount.add(statement);
}
}
String stListFromToAmount=String.format("statement list from and to amount %s", statementListFromToAmount);
    log.info(stListFromToAmount);
        return statementListFromToAmount;
}

public List<Statement> setStatementAccountId(List<Statement> statementListByAccId, StatementDto statementDto )
{
String accountId = String.format("account id %s", statementDto.getAccountId());
log.info(accountId);
        return statementListByAccId;
}


public boolean isAnyParameter(StatementDto statementDto)
{
return statementDto.getAccountId() != null || statementDto.getFromAmount() != null || statementDto.getToAmount() != null
|| statementDto.getFromDate() != null || statementDto.getToDate() != null;
}


}
