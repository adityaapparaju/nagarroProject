package nagarro.nagarroProject.web;

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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import nagarro.nagarroProject.model.Statement;
import nagarro.nagarroProject.service.StatementService;
import nagarro.nagarroProject.web.dto.StatementDto;


@Controller
public class StatementController {

    @Autowired
    private StatementService statementService;
    
    private static final Logger log = LoggerFactory.getLogger(StatementController.class);
    
    private static final String messageAttribute = "message";
    
    private static final String statementListAttribute = "statementList";

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        return "login";
    }
    
  
    
    @ModelAttribute("statement")
    public StatementDto statementDto() {
        return new StatementDto();
    }
    

    
    
    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String mainForm(Model model) {
        model.addAttribute("statement", new StatementDto());

        return "main";
    }
    
    
    
    
    @RequestMapping(value = "/main", method = RequestMethod.POST)
    public String view(Model model, @ModelAttribute("statement") @Valid StatementDto statementDto) {

    	log.info("view statement - start");
    	
    	
    	List<Statement> statementListThreeMonths = new ArrayList<Statement>();
    	List<Statement> statementListFromToAmount = new ArrayList<Statement>();
    	List<Statement> statementListFromToDate = new ArrayList<Statement>();
    	List<Statement> statementListAllDetails = new ArrayList<Statement>();
    	List<Statement> statementListByAccId = new ArrayList<Statement>();
    	List<Statement> statementListFinal = new ArrayList<Statement>();
    	SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
    	String message = "success";
    	try {
    		if (statementDto.getAccountId() != null && statementDto.getAccountId() >=0.0)
    		{
	    	    statementListByAccId =  statementService.findAllByAccountId(statementDto.getAccountId());
    		}
	    	List<Statement> statementList =  statementService.findAll();
	        
	    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();

	    	 if ( auth != null && auth.getAuthorities() != null && auth.getAuthorities().size() > 0 && 
	    			 auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("User1")))
	    	{
	    		 if ( (statementDto.getAccountId() != null && statementDto.getAccountId() < 0.0) ||
	 	    			(statementDto.getFromAmount() != null && statementDto.getFromAmount().compareTo(BigDecimal.ZERO) < 0) || 
	 	    			(statementDto.getToAmount() != null && statementDto.getToAmount().compareTo(BigDecimal.ZERO) < 0) || 
	 	    			(statementDto.getFromAmount() != null && statementDto.getFromAmount().compareTo(BigDecimal.ZERO) >= 0 && 
	 	    			statementDto.getToAmount() != null && statementDto.getToAmount().compareTo(BigDecimal.ZERO) >= 0 &&
	 	    			statementDto.getFromAmount().compareTo(statementDto.getToAmount()) > 0.0) || 
	 	    			(!StringUtils.isEmpty(statementDto.getFromDate())  && !StringUtils.isEmpty(statementDto.getToDate())
	 	    					&& formatter.parse(statementDto.getFromDate()).compareTo(formatter.parse(statementDto.getToDate())) > 0)
	 	    					)
	 	    	{
	 		        log.info("invalid parameter provided");
	 		        message = "Invalid parameter" ;
	 	    	}
	    		else if( statementDto.getAccountId() != null && statementDto.getAccountId() >=0 )
	    		{
	    	
	    			if ((statementDto.getFromAmount() != null && statementDto.getToAmount() == null) 
	    					|| (statementDto.getFromAmount() == null && statementDto.getToAmount() != null)
		    				|| (StringUtils.isEmpty(statementDto.getFromDate()) && !StringUtils.isEmpty(statementDto.getToDate()))
		    				|| (!StringUtils.isEmpty(statementDto.getFromDate()) && StringUtils.isEmpty(statementDto.getToDate())))
	    			{
	    		        log.info("invalid range parameter provided");
		 		        message = "Invalid range parameter" ;
	    		        
	    			}
	    			else if (statementDto.getFromAmount() == null && statementDto.getToAmount() == null
		    				&& StringUtils.isEmpty(statementDto.getFromDate()) && StringUtils.isEmpty(statementDto.getToDate()))
		    		{
			            log.info(" account id : " + statementDto.getAccountId());
			            statementListFinal = statementListByAccId;
			    	}
		        	
		    		else if (statementDto.getFromAmount() != null && statementDto.getToAmount() != null
		    				&& (StringUtils.isEmpty(statementDto.getFromDate()) && StringUtils.isEmpty(statementDto.getToDate())))
		    		{
		                log.info(" account id: " + statementDto.getAccountId() + " ,from amount: " + statementDto.getFromAmount() +
		                		",to amount " + statementDto.getToAmount());
		    			for (Statement statement : statementListByAccId)
		        		{
		        			if (statementDto.getFromAmount().compareTo(statement.getAmount()) <= 0 && 
		        					statementDto.getToAmount().compareTo(statement.getAmount()) >= 0 )
		        					
		        			{
		        				statementListFromToAmount.add(statement);
		        			}
		        		}
		            	log.info("statement list from and to amount: " + statementListFromToAmount);
			            statementListFinal = statementListFromToAmount;
		
		    			
		    		}
		    		
		    		else if (statementDto.getFromAmount() == null && statementDto.getToAmount() == null
		    				&& (!StringUtils.isEmpty(statementDto.getFromDate()) && !StringUtils.isEmpty(statementDto.getToDate())))
		    		{
		                log.info(" account id: " + statementDto.getAccountId() + " from date: " + statementDto.getFromDate()
		                + "to date given: " + statementDto.getToDate());
		    			for (Statement statement : statementListByAccId)
		        		{
		    			    
		    			    if (formatter.parse(statementDto.getFromDate()).compareTo(formatter.parse(statement.getDateField())) <= 0 &&
		    			    		formatter.parse(statementDto.getToDate()).compareTo(formatter.parse(statement.getDateField())) >= 0)
		    			    		
		        			{
		        				statementListFromToDate.add(statement);
		        			}
		        		}
		            	log.info("statement list from and to date: " + statementListFromToDate);
			            statementListFinal = statementListFromToDate;
		
		    			
		    		}
		    		
		    		else if (statementDto.getFromAmount() != null && statementDto.getToAmount() != null
		    				&& (!StringUtils.isEmpty(statementDto.getFromDate()) && !StringUtils.isEmpty(statementDto.getToDate())))
		    		{
		                log.info(" all details given");
		
		               
		    			for (Statement statement : statementListByAccId)
		        		{
		    			    
		    				if (formatter.parse(statementDto.getFromDate()).compareTo(formatter.parse(statement.getDateField())) <= 0 &&
		    			    		formatter.parse(statementDto.getToDate()).compareTo(formatter.parse(statement.getDateField())) >= 0 &&
		    			    		statementDto.getFromAmount().compareTo(statement.getAmount()) <= 0 && statementDto.getToAmount().compareTo(statement.getAmount()) >= 0
		    			    		)	
		        			{
		    			    	statementListAllDetails.add(statement);
		        			}
		        		}
		            	log.info("statement list of all details: " + statementListAllDetails);
			            statementListFinal = statementListAllDetails;
		
		    			
		    		}
	    		}
	    		else
	    		{
	    			message= "please enter account id";
	   			}
	    		
		    		
	    	}
	    	else if (auth != null && auth.getAuthorities() != null && auth.getAuthorities().size() > 0 && 
	    			auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("User2")))
	    	{
		    	if (statementDto.getAccountId()== null && statementDto.getFromAmount() == null && statementDto.getToAmount() == null
		    			&& StringUtils.isEmpty(statementDto.getFromDate()) && StringUtils.isEmpty(statementDto.getToDate()) && auth != null) 

		    	{
		        	log.info("no parameter provided");
		    	    Date currentDate = new Date();
		
		        	log.info("calculating three months back statement");
		        	
		        	Date fromDate;
		    		for (Statement statement : statementList)
		    		{
		    			fromDate=formatter.parse(statement.getDateField());
		    			int fromTime = fromDate.getYear() * 12 + fromDate.getMonth();
		    		    int currentTime = currentDate.getYear() * 12 + currentDate.getMonth();
		        	    
		    		    if (currentTime - fromTime <= 3)
		    			{
		    				statementListThreeMonths.add(statement);
		    
		    			}
		    		}
		        	log.info("statement list of three months: " + statementListThreeMonths);
		            statementListFinal = statementListThreeMonths;
		    	}
		    	else 
		    	{
		    		message = "insufficient privileges";
		    	}
	    	}
	        model.addAttribute(statementListAttribute,statementListFinal);
  
	    	model.addAttribute(messageAttribute,message );
	    	log.info("view statement - end");
	    	
    	}
    	
    	catch(ParseException e)
    	{
    		log.error("Invalid date format" + e);
    		model.addAttribute(messageAttribute,"Invalid date format" );
    		return "main";
    	}
    	catch(Exception e)
    	{
    		log.error("invalid details" + e);
    		model.addAttribute(messageAttribute,"invalid details" );
    		return "main";
    	}

        return "main";

    }
    

    
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String root() {
        return "login";
    }

    
}
