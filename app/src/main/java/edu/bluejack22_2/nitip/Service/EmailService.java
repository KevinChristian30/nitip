package edu.bluejack22_2.nitip.Service;


import android.os.AsyncTask;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailService extends AsyncTask<Void, Void, Void> {
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String mFromEmail;
    private String mPassword;
    private String mToEmail;
    private String mSubject;
    private String mMessage;

    public EmailService(String fromEmail, String password, String toEmail, String subject, String message) {
        mFromEmail = fromEmail;
        mPassword = password;
        mToEmail = toEmail;
        mSubject = subject;
        mMessage = message;
    }

    @Override
    protected Void doInBackground(Void... params) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(mFromEmail, mPassword);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mFromEmail));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(mToEmail));
            message.setSubject(mSubject);
            message.setText(mMessage);

            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public static void isEmailExists(String email, OnCompleteListener<QuerySnapshot> onCompleteListener) {
        CollectionReference usersCollection = db.collection("users"); // Replace "users" with the name of your collection
        Query query = usersCollection.whereEqualTo("email", email); // Replace "email" with the field name you use for storing emails
        query.get().addOnCompleteListener(onCompleteListener);
    }

}
