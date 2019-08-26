package transactions.model;


import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.json.simple.JSONObject;


//@Data
@Entity
@Table(name="transactions")

public class TransactionModel{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private long id;

	@Column(name="user_id")
	@NotNull(message = "User id is mandatory")
	private String userId;
	@Column(name="currency_from")
	@NotNull(message = "Currency From is mandatory")
	private String currencyFrom;
	@Column(name="currency_to")
	@NotNull(message = "Currency To is mandatory")
	private String currencyTo;
	@Column(name="amount_sell")
	@NotNull(message = "Amount Sell is mandatory")
	private double amountSell;
	@Column(name="amount_buy")
	@NotNull(message = "Amount Buy is mandatory")
	private double amountBuy;
	@Column(name="rate")
	@NotNull(message = "Rate is mandatory")
	private double rate;
	@Column(name="timestamp")
	private long timestamp;
	@Column(name="origin_country")
	@NotNull(message = "OriginatingCountry is mandatory")
	private String originatingCountry;
	@Transient
	@NotNull(message = "Time Placed is mandatory")
	private String timePlaced;
		public Long getId() {
			return id;
		}
		public String getUserId() {
			return userId;
		}
		public String getCurrencyFrom() {
			return currencyFrom;
		}
		public String getCurrencyTo() {
			return currencyTo;
		}
		public double getAmountSell() {
			return amountSell;
		}
		public double getAmountBuy() {
			return amountBuy;
		}
		public double getRate() {
			return rate;
		}
		public long getTimestamp() {
			return timestamp;
		}
		public String getTimePlaced() {
			return timePlaced;
		}
		public void setTimePlaced(String timePlaced) {
			this.timePlaced = timePlaced;
		}
		public void setUserId(String userId) {
			this.userId = userId;
		}
		public void setCurrencyFrom(String currencyFrom) {
			this.currencyFrom = currencyFrom;
		}
		public void setCurrencyTo(String currencyTo) {
			this.currencyTo = currencyTo;
		}
		public String getOriginatingCountry() {
			return originatingCountry;
		}
		public void setOriginatingCountry(String originatingCountry) {
			this.originatingCountry = originatingCountry;
		}
		public void setAmountSell(String amountSell_String) {
			this.amountSell = Double.valueOf(amountSell_String);
		}
		public void setAmountBuy(String amountBuy_String) {
			this.amountBuy = Double.valueOf(amountBuy_String);
		}
		public void setRate(String rate_String) {
			this.rate = Double.valueOf(rate_String);
		}
		
		public void setTimestamp(String timePlaced_String, String locationString) {
			String pattern = "dd-MMM-yy HH:mm:ss";
			System.out.println("Location++++++ "+locationString);
			DateTimeFormatter dtf = new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern(pattern).toFormatter(new Locale("en",locationString));
			LocalDateTime ldt = LocalDateTime.from(dtf.parse(timePlaced_String));
			Date date = java.sql.Timestamp.valueOf(ldt);
			Calendar cdt = Calendar.getInstance();
			cdt.setTime(date);	
			this.timestamp =  cdt.getTimeInMillis();
		}
		public TransactionModel() {
		}
		public TransactionModel(JSONObject map) throws ParseException {
			this.userId = String.valueOf(map.get("userId"));
			this.currencyFrom = String.valueOf(map.get("currencyFrom"));
			this.currencyTo = String.valueOf(map.get("currencyTo"));
			setAmountSell(map.get("amountSell")+"");
			setAmountBuy(map.get("amountBuy")+"");
			setRate(map.get("rate")+"");
			setTimestamp(map.get("timePlaced")+"",map.get("originatingCountry")+"");
			this.originatingCountry = String.valueOf(map.get("originatingCountry"));
		}
		public static JSONObject getJSONData(TransactionModel tr) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("userId", tr.userId);
			jsonObj.put("currencyFrom", tr.currencyFrom);
			jsonObj.put("currencyTo", tr.currencyTo);
			jsonObj.put("amountSell", tr.amountSell);
			jsonObj.put("amountBuy", tr.amountBuy);
			jsonObj.put("rate", tr.rate);
			jsonObj.put("timePlaced", tr.timestamp);
			jsonObj.put("originatingCountry", tr.originatingCountry);
			return jsonObj;
		}
		public String toString() {
			return "Transaction [ userId ="+this.userId+" currencyFrom="+this.currencyFrom+" currencyTo="+this.currencyTo+" amountSell="+this.amountSell+" amountBuy="+this.amountBuy
					+" rate="+this.rate+" timePlaced="+this.timestamp+" originaingCountry="+this.originatingCountry+"]";
		}
}
