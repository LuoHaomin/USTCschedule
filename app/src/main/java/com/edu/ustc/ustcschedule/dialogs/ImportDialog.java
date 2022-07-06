package com.edu.ustc.ustcschedule.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.preference.PreferenceManager;

import com.edu.ustc.ustcschedule.R;
import com.edu.ustc.ustcschedule.SQL.MainDatabaseHelper;
import com.edu.ustc.ustcschedule.SQL.MyDeadLine;
import com.edu.ustc.ustcschedule.SQL.MySchedule;
import com.edu.ustc.ustcschedule.web.Login;
import com.edu.ustc.ustcschedule.web.Mycourse;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class ImportDialog extends DialogFragment {

    Context mContext;
    public class Encrypted
    {
        int enc_len;
        byte[] encrypted;

        public byte[] getEncrypted() {
            return encrypted;
        }

        public int getEnc_len() {
            return enc_len;
        }

        public void setEnc_len(int enc_len) {
            this.enc_len = enc_len;
        }

        public void setEncrypted(byte[] encrypted) {
            this.encrypted = encrypted;
        }
    }
    Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String val = data.getString("value");
            Log.i("mylog", "请求结果为-->" + val);
            // TODO
            // UI界面的更新等相关操作
        }
    };
    /**
     * 网络操作相关的子线程
     */
    Runnable networkTask = new Runnable() {
        @Override
        public void run() {
            // TODO
            // 在这里进行 http request.网络请求相关操作
            try {

                final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
                String id= sharedPreferences.getString("id_no","");
                String pwd=sharedPreferences.getString("pwd","");
                byte[] id_byte=id.getBytes();
                byte[] pwd_byte=pwd.getBytes();
                int id_len=sharedPreferences.getInt("id_len",0);
                int pwd_len=sharedPreferences.getInt("pwd_len",0);
                /*try {
                    id_byte=DES_decode(id_byte,id_len,"20220707".getBytes(StandardCharsets.US_ASCII),"32212254".getBytes(StandardCharsets.US_ASCII));
                    pwd_byte=DES_decode(pwd_byte,pwd_len,"20220707".getBytes(StandardCharsets.US_ASCII),"32212254".getBytes(StandardCharsets.US_ASCII));
                } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (InvalidAlgorithmParameterException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                } catch (ShortBufferException e) {
                    e.printStackTrace();
                } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
                } catch (BadPaddingException e) {
                    e.printStackTrace();
                }*/
                for(int i=0;i<id_byte.length;i++)
                {
                    id_byte[i]=(byte)(id_byte[i]^(byte)50);
                }
                for(int i=0;i<pwd_byte.length;i++)
                {
                    pwd_byte[i]=(byte)(pwd_byte[i]^(byte)50);
                }
                String a1=new String(id_byte);
                ArrayList<Mycourse> ans= Login.simulateLogin(new String(id_byte).substring(0,10),new String(pwd_byte),mContext);
                //Toast.makeText(mContext, "课表已导入!", Toast.LENGTH_LONG).show();

            }
            catch (Exception e) {
                e.printStackTrace();
            }
            handler.sendMessage(new Message());
        }
    };
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction

        mContext=getContext();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.import_courses)
                .setMessage(R.string.import_courses_text)
                .setPositiveButton(R.string.OK, (dialog, id) -> {

                    new Thread(networkTask).start();
                    Toast.makeText(getContext(), "课表正在导入!", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton(R.string.cancel, (dialogInterface, i) -> {

                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    public Encrypted DES_encode(byte[] input, byte[] keyBytes, byte[] ivBytes) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, ShortBufferException, IllegalBlockSizeException, BadPaddingException {
        // wrap key data in Key/IV specs to pass to cipher
        SecretKeySpec key = new SecretKeySpec(keyBytes, "DES");
        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
        // create the cipher with the algorithm you choose
        // see javadoc for Cipher class for more info, e.g.
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
        byte[] encrypted= new byte[cipher.getOutputSize(input.length)];
        int enc_len = cipher.update(input, 0, input.length, encrypted, 0);
        enc_len += cipher.doFinal(encrypted, enc_len);
        Encrypted encrypted1=new Encrypted();
        encrypted1.setEncrypted(encrypted);
        encrypted1.setEnc_len(enc_len);
        return encrypted1;
    }
    public byte[] DES_decode(byte[] encrypted, int enc_len, byte[] keyBytes,byte[] ivBytes) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, ShortBufferException, IllegalBlockSizeException, BadPaddingException {
        //byte[] encrypted=encrypted1.getEncrypted();
        //int enc_len=encrypted1.getEnc_len();
        // wrap key data in Key/IV specs to pass to cipher
        SecretKeySpec key = new SecretKeySpec(keyBytes, "DES");
        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
        // create the cipher with the algorithm you choose
        // see javadoc for Cipher class for more info, e.g.
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
        byte[] decrypted = new byte[cipher.getOutputSize(enc_len)];
        int dec_len = cipher.update(encrypted, 0, enc_len, decrypted, 0);
        dec_len += cipher.doFinal(decrypted, dec_len);
        return decrypted;
    }

}