package com.appynitty.gp.activity;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class AboutAppynittyActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView txtToolbarTitle;
    TextView textTermConditionContent, textDesclaimerContent, textCopyrightContent, tvAddressContent;
    TextView textTermConditionTitle, textDesclaimerTitle, textCopyrightTitle, tvSubTitle, tvAddressTitle;
    ImageView imageAboutAppynityHeader;
    int deviceHeight;
    View toolbarView;
//
//    AboutAppynittyDto aboutAppynityDto;
//    private CallingButtonView callingButtonView;
//    private LinearLayout llPhoneNumbers;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_about_appynitty);
//
//        initViews();
//        initListeners();
//        initData();
//        try {
//            setUpToolBar(toolbarView, aboutAppynityDto.getPageTitle(), false);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        AppController.getInstance().setCurrentActivityView(getWindow().getDecorView().findViewById(android.R.id.content));
//    }
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        finish();
//    }
//
//    @Override
//    public void getDataFromLocal() {
//
//    }
//
//    @Override
//    public void getDataFromServer() {
//
//    }
//
//    @Override
//    public void initViews() {
//        toolbarView = findViewById(R.id.layoutToolBar);
//        textTermConditionContent = (TextView) findViewById(R.id.textTermConditionContent);
//        textDesclaimerContent = (TextView) findViewById(R.id.textDesclaimerContent);
//        textCopyrightContent = (TextView) findViewById(R.id.textCopyrightContent);
//        textTermConditionTitle = (TextView) findViewById(R.id.textTermConditionTitle);
//        textDesclaimerTitle = (TextView) findViewById(R.id.textDesclaimerTitle);
//        textCopyrightTitle = (TextView) findViewById(R.id.textCopyrightTitle);
//        tvAddressContent = (TextView) findViewById(R.id.textAddressContent);
//        tvAddressTitle = (TextView) findViewById(R.id.textAddressTitle);
//        tvSubTitle = (TextView) findViewById(R.id.tvSubTitle);
//        imageAboutAppynityHeader = (ImageView) findViewById(R.id.imageAboutAppynityHeader);
//
//        textTermConditionContent.setText("");
//        textCopyrightContent.setText("");
//        textDesclaimerContent.setText("");
//
//        callingButtonView = (CallingButtonView) findViewById(R.id.callingView);
//        callingButtonView.mActivity = this;
//        callingButtonView.phoneNumber = GlobalConstants.CONTACT_APPYNITTY;
//        callingButtonView.setCallingText(getString(R.string.contact_appynitty));
//        llPhoneNumbers = (LinearLayout) findViewById(R.id.ll_phone_numbers);
//        FontHelper.fontRobotoRegular(this, txtToolbarTitle, false);
//        FontHelper.fontRobotoRegular(this, textTermConditionContent, false);
//        FontHelper.fontRobotoRegular(this, textCopyrightContent, false);
//        FontHelper.fontRobotoRegular(this, textDesclaimerContent, false);
//        FontHelper.fontRobotoRegular(this, textCopyrightTitle, true);
//        FontHelper.fontRobotoRegular(this, textDesclaimerTitle, true);
//        FontHelper.fontRobotoRegular(this, textTermConditionTitle, true);
//        FontHelper.fontRobotoRegular(this, tvAddressTitle, true);
//    }
//
//    @Override
//    public void initData() {
//        aboutAppynityDto = AppynittyDatabaseHelper.getInstance(this).getAboutAppynittyData();
//        boolean isTermsAvailable = true;
//        boolean isDisclaimerAvailable = true;
//        boolean isCopyRightAvailable = true;
//
//        if (aboutAppynityDto != null) {
//            if (aboutAppynityDto.getTerms() != null) {
//                if (!aboutAppynityDto.getTerms().isEmpty()) {
//                    textTermConditionContent.setText(aboutAppynityDto.getTerms());
//                } else
//                    isTermsAvailable = false;
//            } else
//                isTermsAvailable = false;
//
//            if (aboutAppynityDto.getDisclaimer() != null) {
//                if (!aboutAppynityDto.getDisclaimer().isEmpty()) {
//                    textDesclaimerContent.setText(aboutAppynityDto.getDisclaimer());
//                } else
//                    isDisclaimerAvailable = false;
//            } else
//                isDisclaimerAvailable = false;
//
//            if (aboutAppynityDto.getCopyRightInfo() != null) {
//                if (!aboutAppynityDto.getCopyRightInfo().isEmpty()) {
//                    textCopyrightContent.setText(aboutAppynityDto.getCopyRightInfo());
//                } else
//                    isCopyRightAvailable = false;
//            } else
//                isCopyRightAvailable = false;
//
//            String address = "Appynitty Communications Pvt Ltd\n" +
//                    "\n" +
//                    "Address: F-5, 2nd Floor,, Premium Plaza, Mata Mandir Rd, Khare Town, Dharampeth, Nagpur, Maharashtra 440010";
//            tvAddressContent.setText(address);
//
//
//            if (!isTermsAvailable) {
//                textTermConditionContent.setVisibility(View.GONE);
//                textTermConditionTitle.setVisibility(View.GONE);
//            }
//
//            if (!isDisclaimerAvailable) {
//                textDesclaimerContent.setVisibility(View.GONE);
//                textDesclaimerTitle.setVisibility(View.GONE);
//            }
//
//            if (!isCopyRightAvailable) {
//                textCopyrightTitle.setVisibility(View.GONE);
//                textCopyrightContent.setVisibility(View.GONE);
//            }
//            if (aboutAppynityDto.getContact1() != null) {
//                if (!aboutAppynityDto.getContact1().isEmpty()) {
//                    llPhoneNumbers.addView(addViewContact(aboutAppynityDto.getContact1()));
//                    callingButtonView.phoneNumber = aboutAppynityDto.getContact1();
//                }
//            }
//            if (aboutAppynityDto.getContact2() != null) {
//                if (!aboutAppynityDto.getContact2().isEmpty())
//                    llPhoneNumbers.addView(addViewContact(aboutAppynityDto.getContact2()));
//            }
//
//            tvSubTitle.setText(aboutAppynityDto.getSubTitle());
//        }
//        setImageViewHeight();
//    }
//
//    private View addViewContact(final String contactNumber) {
//        View view = getLayoutInflater().inflate(R.layout.custom_contact_layout, null);
//        TextView tvMemberName = (TextView) view.findViewById(R.id.tvnumber);
//        tvMemberName.setText(contactNumber);
//
//        view.setPadding(10, 10, 10, 10);
//
//        tvMemberName.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CommonHelper.showCallingDialog(AboutAppynittyActivity.this, contactNumber);
////                AppController.getInstance().makePhoneCall(contactNumber, AboutAppynittyActivity.this);
//            }
//        });
//
//        return view;
//    }
//
//    private void setImageViewHeight() {
//        deviceHeight = CommonHelper.getDeviceHeight(this);
////        imageAboutAppynityHeader.getLayoutParams().height = (int) (CommonHelper.get16by9ratio(CommonHelper.getDeviceWidth(this)));
//    }
//
//    @Override
//    public void initListeners() {
//        textTermConditionTitle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showHideView(textTermConditionContent, textTermConditionTitle);
//            }
//        });
//
//        textCopyrightTitle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showHideView(textCopyrightContent, textCopyrightTitle);
//            }
//        });
//
//        textDesclaimerTitle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showHideView(textDesclaimerContent, textDesclaimerTitle);
//            }
//        });
//
//        tvAddressTitle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showHideView(tvAddressContent, tvAddressTitle);
//            }
//        });
//    }
//
//    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
//    public void showHideView(TextView tvContent, TextView tvItemTitle) {
//        if (tvContent.isShown()) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                tvItemTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icn_plus, 0);
//            } else
//                tvItemTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icn_plus, 0);
////            Animation slideUpAnim = AnimationUtils.loadAnimation(this, R.anim.slide_up);
////            tvContent.setAnimation(slideUpAnim);
//            tvContent.setVisibility(View.GONE);
//        } else {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                tvItemTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icn_minus, 0);
//            } else
//                tvItemTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icn_minus, 0);
////            Animation slideUpAnim = AnimationUtils.loadAnimation(this, R.anim.slide_down);
////            tvContent.setAnimation(slideUpAnim);
//            tvContent.setVisibility(View.VISIBLE);
//        }
//    }
}
