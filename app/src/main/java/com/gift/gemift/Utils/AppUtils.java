package com.gift.gemift.Utils;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.provider.Settings;
import android.util.Base64;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppUtils {

    public static String getConvertDate(String date) {
        Date date1 = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date1 = format.parse(date);
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat dateFormat = new SimpleDateFormat("dd MMM, yyyy", Locale.ENGLISH);
        return dateFormat.format(date1);
    }

    public static String getTimewithAMPM(String time) {
        SimpleDateFormat code12Hours = new SimpleDateFormat("hh:mm");
        Date dateCode12 = null;
        String formatTwelve;
        String results;
        try {
            dateCode12 = code12Hours.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        formatTwelve = code12Hours.format(dateCode12); // 12

        if (formatTwelve.equals(time)) {
            results = formatTwelve + " AM";
        } else {
            results = formatTwelve + " PM";
        }
        return results;
    }


    public static Date convertDate(String date)
    {
        DateFormat format = new SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH);
        Date finalDate = null;
        try {
            finalDate = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return finalDate;
    }
    public static Date getCurrentDateTime() {
        Date currentDate = Calendar.getInstance().getTime();
        return currentDate;
    }

    public static String getFormattedDateString(Date date) {

        try {

            SimpleDateFormat spf = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
            String dateString = spf.format(date);

            Date newDate = spf.parse(dateString);
            spf = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
            return spf.format(newDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String generateHash(String password) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
            md.update(password.getBytes());
            byte[] byteData = md.digest();
            String base64 = Base64.encodeToString(byteData, Base64.NO_WRAP);
            return base64;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void showMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void openKeyboard(final Context context) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                }
            }
        }, 500);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getWindow().getDecorView().getRootView().getWindowToken(), 0);
    }

    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality) {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.NO_WRAP);
    }

    public static Bitmap decodetoBitmap(String imageString) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.toByteArray();
        byte[] imageBytes;
        imageBytes = Base64.decode(imageString, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
    }

    public static Bitmap decodetoBitmapByBase64(String base64) {
        byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

    public static Bitmap getBitmap(String path) {
        Bitmap bitmap = null;
        try {
            File f = new File(path);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            bitmap = BitmapFactory.decodeStream(new FileInputStream(f), null, options);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    private static Boolean emailValidation(String email) {
        boolean isValid = false;
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        isValid = !email.matches(emailPattern) || email.length() <= 0;
        return isValid;
    }


    private static Boolean validPan(String panDetails) {
        boolean isValid = false;
        final String pan_pattern = "(([A-Za-z]{5})([0-9]{4})([a-zA-Z]))";
        Pattern r = Pattern.compile(pan_pattern);
        isValid = !regex_matcher(r, panDetails);
        return isValid;
    }

    private static boolean regex_matcher(Pattern pattern, String string) {
        Matcher m = pattern.matcher(string);
        return m.find() && (m.group(0) != null);
    }

    private static boolean ValidIFSC(String IFSCdetails) {
        boolean isValid = false;
        final String ifsc_pattern = "^[A-Za-z]{4}[a-zA-Z0-9]{7}$";
        Pattern r = Pattern.compile(ifsc_pattern);
        isValid = !regex_matcher(r, IFSCdetails);
        return isValid;
    }




    public static void datePicker(Context mContext, TextView et, String Date) {
        final Calendar myCalendar = Calendar.getInstance();
        int yearq, month, day;
        if (!Date.equals("")) {
            long timeInMillis = getMilliSecFromDate(Date);
            myCalendar.setTimeInMillis(timeInMillis);
        }
        yearq = myCalendar.get(Calendar.YEAR);
        month = myCalendar.get(Calendar.MONTH);
        day = myCalendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog pickerDialog = new DatePickerDialog(mContext, (view, year, monthOfYear, dayOfMonth) -> {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String text = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
            String selectedDate = sdf.format(StringToDateConvertAnother(text));
            et.setText(selectedDate);
//            et.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
        }, yearq, month, day);
        pickerDialog.show();
    }

    public static Date StringToDateConvertAnother(String givenDate) {
        Date date = null;
        try {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            date = sdf.parse(givenDate);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return date;
    }

    public static long getMilliSecFromDate(String datei) // 2015-11-21
    {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date = sdf.parse(datei);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date == null ? System.currentTimeMillis() : date.getTime();
    }

    public static void TimePicker(Context context, TextView et) {
        int mHour, mMinute;
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                (view, hourOfDay, minute) -> et.setText(hourOfDay + ":" + minute), mHour, mMinute, false);
        timePickerDialog.show();
    }

    public static void buildAlertMessageNoGps(Context context) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Please Turn ON your GPS Connection")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)));
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public static boolean compareDate(String StartDate, String EndDate, String SelectedDate) {
        boolean isTrue = false;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);

            Date d1 = sdf.parse(StartDate);
            Date d2 = sdf.parse(SelectedDate);
            Date d3 = sdf.parse(EndDate);

            assert d2 != null;
            if (d2.compareTo(d1) >= 0) {
                isTrue = d2.compareTo(d3) > 0;
            } else {
                isTrue = true;
            }

        } catch (ParseException pe) {
            pe.printStackTrace();
        }

        return isTrue;
    }

    private static boolean compareTime(String FromTime, String EndTime, String SelectedTime) {

        boolean isTrue = false;
        Date date, dateCompareOne, dateCompareTwo;

        date = parseDate(SelectedTime + ":00");
        dateCompareOne = parseDate(FromTime);
        dateCompareTwo = parseDate(EndTime);

        isTrue = !dateCompareOne.before(date) || !dateCompareTwo.after(date);


        return isTrue;
    }

    private static Date parseDate(String date) {
        String inputFormat = "HH:mm";
        SimpleDateFormat inputParser = new SimpleDateFormat(inputFormat, Locale.US);
        try {
            return inputParser.parse(date);
        } catch (ParseException e) {
            return new Date(0);
        }
    }

    public static String getTimeStamp(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date past = null;
        try {
            past = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date now = new Date();
        long seconds = TimeUnit.MILLISECONDS.toSeconds(now.getTime() - past.getTime());
        long minutes = TimeUnit.MILLISECONDS.toMinutes(now.getTime() - past.getTime());
        long hours = TimeUnit.MILLISECONDS.toHours(now.getTime() - past.getTime());
        long days = TimeUnit.MILLISECONDS.toDays(now.getTime() - past.getTime());

        if (seconds < 60) {
            System.out.println(seconds + " seconds ago");
            return seconds + " seconds ago";
        } else if (minutes < 60) {
            System.out.println(minutes + " minutes ago");
            if (minutes == 1)
                return minutes + " minute ago";
            else
                return minutes + " minutes ago";
        } else if (hours < 24) {
            System.out.println(hours + " hours ago");
            if (hours == 1)
                return hours + " hour ago";
            else
                return hours + " hours ago";
        } else {
            System.out.println(days + " days ago");
            if (days == 1)
                return days + " day ago";
            else
                return days + " days ago";

        }
    }

    public static String formatConvert(String rupees) {
        return removeComma(rupees.replaceAll("₹", ""));

    }

    public static String StandaredMoneyConversion(String money) {
        double amount = Double.parseDouble(money);
        DecimalFormat formatter = new DecimalFormat("#,###.00");
        String formatted = formatter.format(amount);
        return formatted;
    }

    public static String removeComma(String money) {
        return money.replaceAll(",", "");
    }

    public static String dateConversion(String scheduleSlot1Date) {
        DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        @SuppressLint("SimpleDateFormat") DateFormat targetFormat = new SimpleDateFormat("dd-MMM ");
        Date date = null;
        try {
            date = originalFormat.parse(scheduleSlot1Date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedDate = targetFormat.format(date);  // 20120821
        return formattedDate;
    }

    public static String getCurrentTime() {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+5:30"));
        Date currentLocalTime = cal.getTime();
        @SuppressLint("SimpleDateFormat") DateFormat date = new SimpleDateFormat("HH");
//        DateFormat date = new SimpleDateFormat("HH:mm a");
// you can get seconds by adding  "...:ss" to it
        date.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
        return date.format(currentLocalTime);
    }

    public static String getCurrentDate() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return df.format(c);
    }

    public static String getCurrentDateCopy() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss", Locale.getDefault());
        return df.format(c);
    }


    @SuppressLint("SimpleDateFormat")
    public static String addDayWithNoOfDays(String selectedDate, int noOfDays) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss");
        try {
            c.setTime(Objects.requireNonNull(sdf.parse(selectedDate)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, noOfDays);    //14 dats add
        sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss");
        Date resultdate = new Date(c.getTimeInMillis());
        return sdf.format(resultdate);

    }


    public static void ChangeLanguage(Context context, String language) {
        LocaleHelper.setLocale(context, language);

    }

    public static int getThemeId(Context context) {
        int nightModeFlags =
                context.getResources().getConfiguration().uiMode &
                        Configuration.UI_MODE_NIGHT_MASK;
        switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_YES:
                nightModeFlags = 1;
                break;

            case Configuration.UI_MODE_NIGHT_NO:
                nightModeFlags = 2;
                break;

            case Configuration.UI_MODE_NIGHT_UNDEFINED:
                nightModeFlags = 1;
                break;
        }

        return nightModeFlags;
    }

    public static String generatePIN() {
        int randomPIN = (int) (Math.random() * 9000) + 1000;
        return String.valueOf(randomPIN);
    }
}