package com.edu.ustc.ustcschedule.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreference;

import com.edu.ustc.ustcschedule.FocusActivity;
import com.edu.ustc.ustcschedule.MainActivity;
import com.edu.ustc.ustcschedule.R;
import com.edu.ustc.ustcschedule.SettingActivity;
import com.edu.ustc.ustcschedule.dialogs.CheckUpdateDialog;
import com.edu.ustc.ustcschedule.dialogs.PersonalInfoDialog;
import com.edu.ustc.ustcschedule.dialogs.SignInDialog;

import java.util.Objects;

public class SettingFragment extends PreferenceFragmentCompat {
    private SharedPreferences sharedPreferences = null;

    @Override
    public void onCreatePreferences(
            @Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.preference, rootKey);

        Preference personal_info = findPreference(getString(R.string.personal_info));
        SwitchPreference profile_check = findPreference(getString(R.string.profile_check));
        Preference check_update = findPreference(getString(R.string.check_update));
        SwitchPreference auto_update = findPreference(getString(R.string.auto_update));
        Preference permission_manager = findPreference(getString(R.string.permission_manager));
        Preference reminder_manager = findPreference(getString(R.string.reminder_manager));
        Preference personalization = findPreference(getString(R.string.personalization));
        Preference focus = findPreference(getString(R.string.focus));


        //用于取值的SharedPreferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (sharedPreferences.getBoolean("@string/profile_check", false)) {
            if (personal_info != null) {
                personal_info.setVisible(true);
            }
        } else {
            if (profile_check != null) {
                profile_check.setChecked(false);
            }
        }

        Objects.requireNonNull(profile_check).setOnPreferenceChangeListener((preference, newValue) -> {
            if (profile_check.equals(preference)) {
                boolean value = (Boolean) (newValue);
                if (value) {
                    SignInDialog signInDialog = new SignInDialog();
                    signInDialog.show(getParentFragmentManager(), "sign_in");
                }
                editor.putBoolean("@string/profile_check", value);
                editor.apply();
                if (personal_info != null) {
                    personal_info.setVisible(value);
                }
            }
            return true;
        });

        Objects.requireNonNull(personal_info).setOnPreferenceClickListener(preference -> {
            PersonalInfoDialog personalInfoDialog = new PersonalInfoDialog();
            personalInfoDialog.show(getParentFragmentManager(), "personal_info");
            return false;
        });

        Objects.requireNonNull(check_update).setOnPreferenceClickListener(preference -> {
            CheckUpdateDialog checkUpdateDialog = new CheckUpdateDialog();
            checkUpdateDialog.show(getParentFragmentManager(), "check_update");
            return false;
        });

        Objects.requireNonNull(auto_update).setOnPreferenceChangeListener((preference, newValue) -> {
            // TODO 自动更新设置
            return true;
        });

        Objects.requireNonNull(permission_manager).setOnPreferenceClickListener(preference -> {
            // TODO 权限管理
            return false;
        });

        Objects.requireNonNull(reminder_manager).setOnPreferenceClickListener(preference -> {
            // TODO 通知管理
            return false;
        });

        Objects.requireNonNull(personalization).setOnPreferenceClickListener(preference -> {
            // TODO 个性化
            return false;
        });

        Objects.requireNonNull(focus).setOnPreferenceClickListener(preference -> {
            Intent intent = new Intent(getActivity(), FocusActivity.class);
            startActivity(intent);
            return false;
        });
    }
}