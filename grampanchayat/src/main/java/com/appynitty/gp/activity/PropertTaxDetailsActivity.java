package com.appynitty.gp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.appynitty.gp.R;
import com.appynitty.gp.pojo.PropertyTaxPojo;
import com.appynitty.gp.utils.AUtils;
import com.appynitty.gp.utils.LocaleHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mithsoft.lib.activity.BaseActivity;
import com.payumoney.core.PayUmoneySdkInitializer;
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager;

import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import quickutils.core.QuickUtils;

public class PropertTaxDetailsActivity extends AppCompatActivity {

    TextView propertyHouseNumber, propertyAssessmentDate, propertyFormNo, propertyWardName, propertyMohallaName, propertyOwnershipType,
            propertyOwnerName, propertyHouseNo, propertyHouseAddress, propertyMobileNo, propertyRentArea,
            propertyOwnArea, propertyArea, propertyAreaRates, propertyRoadWidthLess, propertyConstruction, propertyTypeConstruction,
            propertyArv, propertyDepreciation, propertyAppreciation, propertyFinalArv, propertyCurrentYearTax, propertyArrear, propertyInterest,
            propertyTotalDeposit, propertyTaxDiscount, propertyTotalTax;

    Button onlinePayment;

    String MERCHANTID, KEY, SALT, txnid, amount, productinfo, firstname, email, mobileNo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initComponent();
    }

    private void initComponent(){
        generateId();
        registerEvents();
        initData();
    }


    protected void generateId() {
        setContentView(R.layout.activity_propert_tax_details);
        propertyHouseNumber = findViewById(R.id.property_house_number);
        propertyAssessmentDate = findViewById(R.id.property_assessment_date);
        propertyFormNo = findViewById(R.id.property_form_no);
        propertyWardName = findViewById(R.id.property_ward_name);
        propertyMohallaName = findViewById(R.id.property_mohalla_name);
        propertyOwnershipType = findViewById(R.id.property_ownership_type);
        propertyOwnerName = findViewById(R.id.property_owner_name);
        propertyHouseNo = findViewById(R.id.property_house_no);
        propertyHouseAddress = findViewById(R.id.property_house_address);
        propertyMobileNo = findViewById(R.id.property_mobile_no);
        propertyRentArea = findViewById(R.id.property_rent_area);
        propertyOwnArea = findViewById(R.id.property_own_area);
        propertyArea = findViewById(R.id.property_area);
        propertyAreaRates = findViewById(R.id.property_area_rates);
        propertyRoadWidthLess = findViewById(R.id.property_road_width_less);
        propertyConstruction = findViewById(R.id.property_construction);
        propertyTypeConstruction = findViewById(R.id.property_type_construction);
        propertyArv = findViewById(R.id.property_arv);
        propertyDepreciation = findViewById(R.id.property_depreciation);
        propertyAppreciation = findViewById(R.id.property_appreciation);
        propertyFinalArv = findViewById(R.id.property_final_arv);
        propertyCurrentYearTax = findViewById(R.id.property_current_year_tax);
        propertyArrear = findViewById(R.id.property_arrear);
        propertyInterest = findViewById(R.id.property_interest);
        propertyTotalDeposit = findViewById(R.id.property_total_deposit);
        propertyTaxDiscount = findViewById(R.id.property_tax_discount);
        propertyTotalTax = findViewById(R.id.property_total_tax);
        onlinePayment = findViewById(R.id.online_payment);
        initToolbar();
    }

    @Override
    protected void attachBaseContext(Context base) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            super.attachBaseContext(LocaleHelper.onAttach(base));
        }else{
            super.attachBaseContext(base);
        }
    }

    protected void registerEvents() {
        onlinePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PropertTaxDetailsActivity.this, PaymentGatewayWebpageActivity.class));
//                initiatePayment();
            }
        });
    }

    private void initiatePayment() {
        KEY = "XE63fjFz";
        SALT = "jH9z1ixBW9";
        MERCHANTID = "6490756";
        txnid = "test_prod";
        amount = "1";
        productinfo = "Test Product";
        firstname = "Ayan";
        email = "ayand@appynitty.com";
        mobileNo = "9405892926";

        prepareForPayment();

    }

    public static String hashCal(String type, String hashString) {
        StringBuilder hash = new StringBuilder();
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance(type);
            messageDigest.update(hashString.getBytes());
            byte[] mdbytes = messageDigest.digest();
            for (byte hashByte : mdbytes) {
                hash.append(Integer.toString((hashByte & 0xff) + 0x100, 16).substring(1));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hash.toString();
    }

    private void prepareForPayment() {
        try{
            String hashSequence = KEY+"|"+txnid+"|"+amount+"|"+productinfo+"|"+firstname+"|"+email+"|"+SALT;
            String serverCalculatedHash = hashCal("SHA-512", hashSequence);

            PayUmoneySdkInitializer.PaymentParam.Builder builder = new PayUmoneySdkInitializer.PaymentParam.Builder();
            builder.setAmount(amount)                          // Payment amount
                    .setTxnId(txnid)                    // Transaction ID
                    .setPhone(mobileNo)                 // User Phone number
                    .setProductName(productinfo)         // Product Name or description
                    .setFirstName(firstname)                              // User First name
                    .setEmail(email)                                            // User Email ID
                    .setsUrl("")                    // Success URL (surl)
                    .setfUrl("")                     //Failure URL (furl)
                    .setUdf1("")
                    .setIsDebug(true)                              // Integration environment - true (Debug)/ false(Production)
                    .setKey(KEY)                        // Merchant KEY
                    .setMerchantId(MERCHANTID);             // Merchant ID

            //declare paymentParam object
            PayUmoneySdkInitializer.PaymentParam paymentParam = builder.build();
            //set the hash
            paymentParam.setMerchantHash(serverCalculatedHash);

            // Invoke the following function to open the checkout page.
            PayUmoneyFlowManager.startPayUMoneyFlow(paymentParam,PropertTaxDetailsActivity.this,
            R.style.AppTheme_NoActionBar, true);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    protected void initData() {
        PropertyTaxPojo propertyTaxPojo;
        Type type = new TypeToken<PropertyTaxPojo>() {
        }.getType();

        propertyTaxPojo = new Gson().fromJson(
                QuickUtils.prefs.getString(AUtils.PREFS.PROPERTY_TAX_DETAILS_POJO_LIST, null), type);

        if(!AUtils.isNull(propertyTaxPojo)){
            populateData(propertyTaxPojo);
        }
    }

    private void populateData(PropertyTaxPojo propertyTaxPojo) {

        propertyHouseNumber.setText(propertyTaxPojo.getHouseNo());
        propertyAssessmentDate.setText(propertyTaxPojo.getAssessmentDate());
        propertyFormNo.setText(propertyTaxPojo.getFormNo());
        propertyWardName.setText(propertyTaxPojo.getWardName());
        propertyMohallaName.setText(propertyTaxPojo.getMohallaName());
        propertyOwnershipType.setText(propertyTaxPojo.getOwnershipType());
        propertyOwnerName.setText(propertyTaxPojo.getOwnerName());
        propertyHouseNo.setText(propertyTaxPojo.getHouseNo());
        propertyHouseAddress.setText(propertyTaxPojo.getProperty_HouseAddress());
        propertyMobileNo.setText(propertyTaxPojo.getMobileNo());
        propertyRentArea.setText(propertyTaxPojo.getTotalRentareainSqFeet());
        propertyOwnArea.setText(propertyTaxPojo.getTotalOwnAreainSqFeet());
        propertyArea.setText(propertyTaxPojo.getTotalOwnAreainSqFeet());
        propertyAreaRates.setText(propertyTaxPojo.getAreaRates());
        propertyRoadWidthLess.setText(propertyTaxPojo.getRoadWidth());
        propertyConstruction.setText(propertyTaxPojo.getConstructionYear());
        propertyTypeConstruction.setText(propertyTaxPojo.getTypeofConstruction());
        propertyArv.setText(propertyTaxPojo.getAnnualRentalValue());
        propertyDepreciation.setText(propertyTaxPojo.getDepreciation());
        propertyAppreciation.setText(propertyTaxPojo.getAppreciation());
        propertyFinalArv.setText(propertyTaxPojo.getFinalAnnualRentalValue());
        propertyCurrentYearTax.setText(propertyTaxPojo.getCurrentYearTax());
        propertyArrear.setText(propertyTaxPojo.getArrear());
        propertyInterest.setText(propertyTaxPojo.getInterest());
        propertyTotalDeposit.setText(propertyTaxPojo.getTotalDeposite());
        propertyTaxDiscount.setText(propertyTaxPojo.getDiscountOnCurrentTax());
        propertyTotalTax.setText(propertyTaxPojo.getTotaltaxDue());


    }

    private void initToolbar() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.property_details));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.property_pdf, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            PropertTaxDetailsActivity.this.finish();
            return true;
        } else if (item.getItemId() == R.id.action_property_pdf) {

            return true;
        }


        return super.onOptionsItemSelected(item);
    }

}
