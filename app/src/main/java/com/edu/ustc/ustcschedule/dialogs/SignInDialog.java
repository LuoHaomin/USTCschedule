package com.edu.ustc.ustcschedule.dialogs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.preference.PreferenceManager;

import com.edu.ustc.ustcschedule.R;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Objects;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.security.auth.callback.Callback;

public class SignInDialog extends DialogFragment {

    SharedPreferences sharedPreferences = null;

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        try {
//            Callback callback = (Callback) getParentFragment();
//        } catch (ClassCastException e) {
//            throw new ClassCastException("Calling fragment must implement Callback interface");
//        }
//    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_sign_in, null);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();
        builder.setTitle(R.string.sign_in)
                .setView(view)
                .setPositiveButton(R.string.sign_in, (dialog, id) -> {
                    EditText idEditText = view.findViewById(R.id.ID);
                    EditText pwdEditText = view.findViewById(R.id.password);
                    byte[] id_byte = idEditText.getText().toString().getBytes();
                    byte[] pwd_byte = pwdEditText.getText().toString().getBytes();
                    int id_len=0;
                    int pwd_len=0;
                    for(int i=0;i<id_byte.length;i++)
                    {
                        id_byte[i]=(byte)(id_byte[i]^(byte)50);
                    }
                    for(int i=0;i<pwd_byte.length;i++)
                    {
                        pwd_byte[i]=(byte)(pwd_byte[i]^(byte)50);
                    }

                    /*
                    try {
                        Encrypted id_enc=DES_encode(id_byte,"20220707".getBytes(StandardCharsets.US_ASCII),"32212254".getBytes(StandardCharsets.US_ASCII));
                        id_byte=id_enc.getEncrypted();
                        id_len=id_enc.getEnc_len();
                        Encrypted pwd_enc=DES_encode(pwd_byte,"20220707".getBytes(StandardCharsets.US_ASCII),"32212254".getBytes(StandardCharsets.US_ASCII));
                        pwd_byte=pwd_enc.getEncrypted();
                        pwd_len=pwd_enc.getEnc_len();
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

                    editor.putString("id_no", new String(id_byte));
                    editor.putString("pwd", new String(pwd_byte));
                    editor.putInt("id_len",id_len);
                    editor.putInt("pwd_len",pwd_len);
                    editor.apply();
                })
                .setNegativeButton(R.string.cancel, (dialogInterface, i) -> {
                    editor.putString("id_no", "");
                    editor.putString("pwd", "");
                    editor.apply();
                    Objects.requireNonNull(SignInDialog.this.getDialog()).cancel();
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
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
    public Encrypted DES_encode(byte[] input,byte[] keyBytes,byte[] ivBytes) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, ShortBufferException, IllegalBlockSizeException, BadPaddingException {
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