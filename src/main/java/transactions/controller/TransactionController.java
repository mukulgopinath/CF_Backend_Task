package transactions.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.Valid;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import transactions.model.TransactionModel;
import transactions.repository.TransactionRepository;

@RestController

public class TransactionController {
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired 
	TransactionRepository transactionRepository;
	@PostMapping(path="/addTransaction", produces="application/json")
	public @ResponseBody JSONObject addTransaction(@Valid @RequestBody TransactionModel model) throws JsonProcessingException {
		model.setTimestamp(model.getTimePlaced(),model.getOriginatingCountry());
	    System.out.println(model.toString());
        transactionRepository.save(model);
        List<TransactionModel> lst = (List<TransactionModel>) transactionRepository.findAll();
        System.out.println("List size "+lst.size());
        //Map<String,String> map = new HashMap<String,String>();//List<TransactionModel>> map = new HashMap<Integer,List<TransactionModel>>();
        //map.put(lst.size()+"",lst.toString());
        JSONObject jsonobj = new JSONObject();
        jsonobj.put("response", lst);
        jsonobj.put("size", lst.size()+"");
        jsonobj.put("message","success");
        return jsonobj;
	}
	@PostMapping(path="/getTransactionById", produces="application/json")
	public @ResponseBody Optional<TransactionModel> getTransactionById(@RequestBody JSONObject jsonOb) {
		Long id = jsonOb.get("id")!=null?Long.valueOf(jsonOb.get("id")+""):null;
		Optional<TransactionModel> tpm = transactionRepository.findById(id);
		return tpm;
	}
	@PostMapping(path="/getTransactionsByUserId", produces="application/json")
	 public @ResponseBody List<TransactionModel> getTransactionByUserId(@RequestBody JSONObject jsonOb) {
//		if(jsonOb.get("userId")!=null) {
			Long id = Long.valueOf(jsonOb.get("userId")+"");
			List<TransactionModel> tpm = transactionRepository.findTransactionByUserId(id);
			return tpm;
			
//		}
	}
	@PostMapping(path="getTransactionsWithFilters", produces="application/json")
	public @ResponseBody Map<Integer,List<TransactionModel>> getTransactionsBetween(@RequestBody JSONObject jsonOb){
		try {
			long starttime, endtime;
			String fromDate = jsonOb.get("fromDate")!=null?String.valueOf(jsonOb.get("fromDate")):null;
			String toDate = jsonOb.get("fromDate")!=null?String.valueOf(jsonOb.get("toDate")):null;
			String currencyFrom = jsonOb.get("currencyFrom")!=null?String.valueOf(jsonOb.get("currencyFrom")):null;
			String currencyTo = jsonOb.get("currencyTo")!=null?String.valueOf(jsonOb.get("currencyTo")):null;
			String userId = jsonOb.get("userId")!=null?String.valueOf(jsonOb.get("userId")):null;
		   
		    Calendar cdt = Calendar.getInstance(); 
		    
		    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		    CriteriaQuery<TransactionModel> q = cb.createQuery(TransactionModel.class);
		    Root<TransactionModel> c = q.from(TransactionModel.class);
		    ParameterExpression<Integer> p = cb.parameter(Integer.class);
		    q.select(c);
		    Predicate pred = null;
		    
			if(fromDate != null) {
				Date fDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(fromDate);
				cdt.setTime(fDate);
				starttime = cdt.getTimeInMillis();
				pred = cb.gt(c.get("timestamp"), starttime);
			    if(toDate == null) {
			    	pred = cb.and(cb.lt(c.get("timestamp"), System.currentTimeMillis()));
			    }else {
			    	Date tDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(toDate);
					cdt = Calendar.getInstance();
					cdt.setTime(tDate);
					endtime = cdt.getTimeInMillis();
					pred = cb.and(pred,cb.lt(c.get("timestamp"), endtime));
			    }
			}else {
				cdt = Calendar.getInstance();
				int year = cdt.get(Calendar.YEAR);
				int month = cdt.get(Calendar.MONTH);
				int day = cdt.get(Calendar.DATE);
				cdt.set(year, month, day, 0, 0, 0); // start of day
				pred = cb.gt(c.get("timestamp"),cdt.getTimeInMillis());
				cdt.set(year, month, day, 23, 59, 59); // end of day
				pred = cb.and(pred,cb.lt(c.get("timestamp"),cdt.getTimeInMillis()));
			}
			if(currencyFrom != null) {
				pred = cb.and(pred,cb.equal(c.get("currency_from"), currencyFrom));
			}
			if(currencyTo != null) {
				pred = cb.and(pred,cb.equal(c.get("currency_to"), currencyTo));
			}
			if(userId != null) {
				pred = cb.and(pred,cb.equal(c.get("user_id"), userId));
			}
			q.where(pred);
			
		    TypedQuery<TransactionModel> query = entityManager.createQuery(q);
			List<TransactionModel> tpm = query.getResultList();
			int size = tpm.size();
			Map<Integer,List<TransactionModel>> map = new HashMap<Integer,List<TransactionModel>>();
	        map.put(size,tpm);
	        return map;
	        
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	@GetMapping(path="/getAllTransactions", produces = "application/json")
	public @ResponseBody Iterable<TransactionModel> getAllTransactions() {
	    return (List<TransactionModel>) transactionRepository.findAll();
	}
}
