package com.watersystem.app.Includes;


import java.text.DecimalFormat;
import java.util.Date;

public class Report {

    private static  int strLength = 32;

    private String billing_date;
    private String due_date;
    private String location;
    private String fullname;
    private String meter;
    private String previous_reading;
    private String previous_meter;
    private String previous_balance;
    private String current_reading;
    private String current_meter;
    private String cubicmeter_used;
    private String current_balance;
    private String current_total;
    private String cash_tendered;
    private String new_balance;
    private String reader;
    private String or_reciept;

    public Report(){

    }

    public Report(
            String billing_date,                //0
            String due_date,                    //1
            String location,                    //2
            String fullname,                    //3
            String meter,                       //5
            String previous_reading,            //6
            String previous_meter,              //7
            String previous_balance,            //8
            String current_reading,             //9
            String current_meter,               //10
            String cubicmeter_used,             //11
            String current_balance,             //12
            String current_total,               //13
            String cash_tendered,               //14
            String new_balance,                 //15
            String reader,                      //16
            String or_reciept
    ) {
        this.billing_date = billing_date;
        this.due_date = due_date;
        this.location = location;
        this.fullname = fullname;
        this.meter = meter;
        this.previous_reading = previous_reading;
        this.previous_meter = previous_meter;
        this.previous_balance = previous_balance;
        this.current_reading = current_reading;
        this.current_meter = current_meter;
        this.cubicmeter_used = cubicmeter_used;
        this.current_balance = current_balance;
        this.current_total = current_total;
        this.cash_tendered = cash_tendered;
        this.new_balance = new_balance;
        this.reader = reader;
        this.or_reciept = or_reciept;
    }


    public String getDate_today()
    {
        final java.text.DateFormat df = new java.text.SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa");
        final Date dateobj = new Date();

        String	str = "";

        for (int x =0; x < strLength - (df.format(dateobj).length() +1); x++ ){
            str += " ";
        }

        return str +  df.format(dateobj) + " ";
    }

    public String getBilling_date() {

        String label = "Billing Date: ";
        String	str = billing_date;

        for (int x =0; x < strLength - (billing_date.length() + label.length()); x++ ){
            str += " ";
        }

        return label + str ;
    }
    public String getDue_date() {
        String label = "Due Schedule: ";
        String	str = due_date;

        for (int x =0; x < strLength - (due_date.length() + label.length()); x++ ){
            str += " ";
        }

        return label + str ;

        //        return due_date;
    }
    public String getFullname() {
        String label = "Name: ";
        String	str = fullname;

        for (int x =0; x < strLength - (fullname.length() + label.length()); x++ ){
            str += " ";
        }

        return label + str ;
    }
    public String getLocation() {
        String label = "Add: ";
        String	str = location;

        for (int x =0; x < strLength - (location.length() + label.length()); x++ ){
            str += " ";
        }

        return label + str ;
    }



    public String getMeter() {
        String label = "Meter  #: ";
        String	str = meter;

        for (int x =0; x < strLength - (meter.length() + label.length()); x++ ){
            str += " ";
        }

        return label + str;
    }
    public String getPrevious_reading() {
        String label = "Bill From : ";
        String	str = previous_reading;

        for (int x =0; x < strLength - (previous_reading.length() + label.length()); x++ ){
            str += " ";
        }

        return label +str ;
    }
    public String getPrevious_meter() {

        String label = "Consumed  : ";

        String	str = previous_meter;

        for (int x =0; x < strLength - (previous_meter.length() + label.length()); x++ ){
            str += " ";
        }

        return label +str ;
    }


    public String getCurrent_reading() {
        String label = "Bill To   : ";
        String	str = current_reading;

        for (int x =0; x < strLength - (current_reading.length() + label.length()); x++ ){
            str += " ";
        }

        return label + str ;
    }

    public String getCurrent_meter() {
        String label = "Consumed  : ";
        String	str = current_meter;

        for (int x =0; x < strLength - (current_meter.length() + label.length()); x++ ){
            str += " ";
        }

        return label + str ;
    }

    public String getCubicmeter_used() {
        String label = "CU.M Used : ";
        String	str = cubicmeter_used;

        for (int x =0; x < strLength - (cubicmeter_used.length() + label.length()); x++ ){
            str += " ";
        }

        return label + str ;
    }


    //-----------------------------------------------

    public String getPrevious_balance() {
        DecimalFormat formatter = null;
        double amount = Double.parseDouble(previous_balance);
        formatter = new DecimalFormat("#,###.00");

        String val = (previous_balance != "0" ? formatter.format(amount) : "0.00");

        String label = "Prv Balance:";
        String	str = "";

        for (int x =0; x < strLength - (val.length() +  label.length() + 3); x++ ){
            str += " ";
        }

        return label +str +"P "+ val + " ";
    }


    public String getCurrent_balance() {

        DecimalFormat formatter = null;
        double amount = Double.parseDouble(current_balance);
        formatter = new DecimalFormat("#,###.00");

        String val = (current_balance != "0" ? formatter.format(amount) : "0.00");

        String label = "Curr Amount: ";
        String	str = "";

        for (int x =0; x < strLength - (val.length() +  label.length() + 3); x++ ){
            str += " ";
        }

        return label +str +"P "+ val + " ";
    }

    public String getCurrent_total() {

        DecimalFormat formatter = null;
        double amount = Double.parseDouble(current_total);
        formatter = new DecimalFormat("#,###.00");

        String val = (current_total != "0" ? formatter.format(amount) : "0.00");

        String label = "Total: ";
        String	str = "";

        for (int x =0; x < strLength - (val.length() +  label.length() + 3); x++ ){
            str += " ";
        }

        return label +str +"P "+ val + " ";
    }

    public String getNew_balance() {

        DecimalFormat formatter = null;
        double amount = Double.parseDouble(new_balance);
        formatter = new DecimalFormat("#,###.00");

        String val = (new_balance != "0" ? formatter.format(amount) : "0.00");

        String label = "New Balance : ";
        String	str = "";

        for (int x =0; x < strLength - (val.length() +  label.length() + 3); x++ ){
            str += " ";
        }

        return label +str +"P "+ val + " ";


    }
    public String getReader() {
        String	str = "";

        for (int x =0; x < ((strLength-reader.length())/2); x++ ){
            str += " ";
        }
        return str + reader + str + ((reader.length()%2 == 1)? " ": "");
    }

    public String getCash_tendered() {

        DecimalFormat formatter = null;
        double amount = Double.parseDouble(cash_tendered);
        formatter = new DecimalFormat("#,###.00");

        String val = "";

        val = formatter.format(amount);

        String label = "Cash Tendered : ";
        String	str = "";

        for (int x =0; x < strLength - (val.length() +  label.length() + 3); x++ ){
            str += " ";
        }

        double data = Double.parseDouble(cash_tendered);
        if(data != 0){
            return label +str +"P "+ val + " ";
        }

        return "";
    }

    public String getOr_reciept() {
        String	str = or_reciept;
        String label = "O.R #: ";

        for (int x =0; x < strLength - ( or_reciept.length() + label.length()); x++ ){
            str += " ";
        }

        double data = Double.parseDouble(cash_tendered);
        if(data != 0){
            return label + str ;
        }

        return "";
    }


    public String parseHeader(){

        return     "                                "
                +  "       CITY OF GIHULNGAN        "
                +  "        NEGROS ORIENTAL         "
                +  "                                "
                +  "         Office of The          "
                +  "      Workworks Operation       "
                +  "                                "
                +  "      W A T E R   B I L L       "
                +  "                                ";
    }

    public String parseBlank(){
        return     "                                ";
    }


    public String boundery(){
return       "              ----------------- ";
    }

    public String signature(){

        return 		                        "Meter Reader :                  "
                                         +  "                                "
                                         +  "   __________________________   ";
    }

    public String message(){

        return  parseBlank() +
                parseBlank() +
                parseHeader() +
                getDate_today()+
                parseBlank() +
                getFullname() +
                getLocation() +
                getBilling_date() +
                getDue_date()+
                getMeter() +
                parseBlank() +
                getPrevious_reading() +
                getPrevious_meter()  +
                getPrevious_balance() +
                getCurrent_reading() +
                getCurrent_meter() +
                getCubicmeter_used() +
                getCurrent_balance() +
                boundery() +
                getCurrent_total() +
                getCash_tendered() +
                boundery()+
                getNew_balance()+
                parseBlank()+
                getOr_reciept()+
                parseBlank()+
                signature()+
                getReader()+
                parseBlank()+
                parseBlank()+
                parseBlank()+
                parseBlank();
        }
    }
