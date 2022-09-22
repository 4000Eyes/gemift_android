package com.gift.gemift.Ui.DashBoard.Fragments.Friends;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Filter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import io.michaelrocks.libphonenumber.android.NumberParseException;
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import com.gift.gemift.Model.ContactsInfo;
import com.gift.gemift.R;
import com.gift.gemift.Storage.AppPreference.AppPreferences;
import com.gift.gemift.Ui.Adaptor.ContactAdaptor;
import com.gift.gemift.Utils.FilterHelper;
import com.gift.gemift.Utils.Utils;
import com.gift.gemift.databinding.ContactListBinding;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class PhoneContacts_Activity extends AppCompatActivity {
    private ContactListBinding binding;
    ArrayList<ContactsInfo> arrayList = new ArrayList<>();
    private CompositeDisposable disposable = new CompositeDisposable();
    ContactAdaptor contactAdaptor;
    String defaultCountryCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ContactListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AppPreferences appPreferences = new AppPreferences(this);

        defaultCountryCode = appPreferences.getCountryCode();


        setSupportActionBar(binding.appbar.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        final Drawable upArrow = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_arrow, null);
        FilterHelper.setColorFilter(upArrow, getResources().getColor(R.color.white), FilterHelper.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        binding.appbar.txtTitle.setText("Search contacts");


        disposable.add(RxTextView.textChangeEvents(binding.editSearchItem)
                .skipInitialValue()
                .debounce(300, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<TextViewTextChangeEvent>() {

                    @Override
                    public void onNext(@NonNull TextViewTextChangeEvent textViewTextChangeEvent) {
                        if (contactAdaptor != null) {
                            contactAdaptor.getFilter().filter(textViewTextChangeEvent.text());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                })
        );


        LoadContacts();
    }

    private void LoadContacts() {

        Uri uri = ContactsContract.Contacts.CONTENT_URI;

        String sort = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME;

        Cursor cursor = getContentResolver().query(
                uri,null,null,null,sort
        );

        if(cursor.getCount() > 0){
            while (cursor.moveToNext()){
                @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                 @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                 Uri uriPhone = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                 String selection = ContactsContract.CommonDataKinds.Phone.CONTACT_ID+" =?";

                 Cursor phoneCursor = getContentResolver().query(
                         uriPhone,null,selection,new String[]{id},null);


                 if (phoneCursor.moveToNext()){
                     @SuppressLint("Range") String number = phoneCursor.getString((phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                     if(number.startsWith("+")){
                         String countryCode = "";
                         String phoneNumber = "";
                         try {
                             countryCode = Utils.getCountryCode(number,this);
                             phoneNumber = Utils.removeCountryCode(number,this);

                         } catch (NumberParseException e) {
                             e.printStackTrace();
                         }

                         Log.i("My Phone", ""+phoneNumber);

                         String Mobile  = number.replace("+"+countryCode,"");

                         ContactsInfo model = new ContactsInfo();
                         String mobile = Mobile.replaceAll("[^0-9]", "");
                         if(countryCode.isEmpty()){
                             countryCode = defaultCountryCode;
                         }
                        if(countryCode.equals("91")||countryCode.equals("1")){
                            model.setDisplayName(name);
                            model.setPhoneNumber(mobile);
                            model.setCountryCode(countryCode);
                            arrayList.add(model);
                        }
                     }else {
                        if(number.length()==10){
                            ContactsInfo model = new ContactsInfo();
                            model.setDisplayName(name);
                            model.setPhoneNumber(number);
                            model.setCountryCode("");
                            arrayList.add(model);
                        }
                     }


                     phoneCursor.close();

                 }
            }
            cursor.close();
        }
         contactAdaptor = new ContactAdaptor(arrayList, this, binding);

        binding.recycleContactList.setAdapter(contactAdaptor);
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.clear();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();

        }
        return true;
    }


}