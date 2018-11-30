package com.buyint.mergerbot.Utility;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.buyint.mergerbot.R;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;

import java.util.ArrayList;

/**
 * Created by CXC on 2018/4/26
 */

public class ViewHelper {

    public static void showWheelViewDialog(Context context, String title, ArrayList<String> list, StringListener listener) {
        View view = LayoutInflater.from(context).inflate(R.layout.wheelview_dialog, null);
        WheelView wv = view.findViewById(R.id.wheelview_dialog_wv);
        wv.setWheelAdapter(new ArrayWheelAdapter(context));
        wv.setWheelData(list);
        wv.setLoop(true);
        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
        style.selectedTextColor = Color.BLACK;
        style.textColor = Color.GRAY;
        style.selectedTextSize = 20;
        wv.setStyle(style);

        MaterialDialog.Builder builder = new MaterialDialog.Builder(context);
        builder.title(title);
        builder.positiveText(R.string.alright);
        builder.negativeText(R.string.cancel);
        builder.positiveColorRes(R.color.colorBlack);
        builder.negativeColorRes(R.color.colorBlack);
        builder.customView(view, false);
        builder.onNegative((dialog, which) -> dialog.dismiss());
        builder.onPositive((dialog, which) -> {
            int position = wv.getCurrentPosition();
            String s = list.get(position);
            if (listener != null) {
                listener.getStrings(s);
            }
            dialog.dismiss();
        });
        builder.build().show();
    }

    public static void showNoticePopupWindowAuto(Context context, View view, int type, View.OnClickListener listener1, View.OnClickListener listener2) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.popup_item_notice, null, false);
        LinearLayout ll1 = inflate.findViewById(R.id.pop_item_notice_ll1);
        LinearLayout ll2 = inflate.findViewById(R.id.pop_item_notice_ll2);
        ImageView imageView = inflate.findViewById(R.id.popup_item_notice_imageview);
        TextView tvTop = inflate.findViewById(R.id.popup_item_notice_top);
        TextView tvBottom = inflate.findViewById(R.id.popup_item_notice_bottom);
        switch (type) {
            case 0:
                Glide.with(context).load(R.mipmap.guanzhu).into(imageView);
                tvTop.setText(context.getString(R.string.guanzhu));
                tvBottom.setText(context.getString(R.string.click_to_guanzhu));
                break;
            case 1:
                Glide.with(context).load(R.mipmap.quxiaoguanzhu).into(imageView);
                tvTop.setText(context.getString(R.string.quxiaoguanzhu));
                tvBottom.setText(context.getString(R.string.click_to_quxiaoguanzhu));
                break;
            case 2:
                Glide.with(context).load(R.mipmap.delete).into(imageView);
                tvTop.setText(context.getString(R.string.delete));
                tvBottom.setText(context.getString(R.string.click_to_delete));
                break;
        }
        PopupWindow pop = new PopupWindow(inflate, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        pop.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pop.setOutsideTouchable(true);
        pop.setTouchable(true);
        pop.setAnimationStyle(R.style.popupwindow_anim_notice);
        Window window = ((Activity) context).getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.alpha = 0.4f;
        window.setAttributes(layoutParams);
        pop.setOnDismissListener(() -> {
            layoutParams.alpha = 1f;
            window.setAttributes(layoutParams);
        });
        ll1.setOnClickListener(v -> {
            pop.dismiss();
            listener1.onClick(inflate);
        });
        ll2.setOnClickListener(v -> {
            pop.dismiss();
            listener2.onClick(inflate);
        });

        int windowPos[] = calculatePopWindowPos(context, view, inflate);
        pop.showAtLocation(view, Gravity.TOP | Gravity.START, windowPos[0], windowPos[1]);
    }

    private static int[] calculatePopWindowPos(Context context, final View anchorView, final View contentView) {
        final int windowPos[] = new int[2];
        final int authorLoc[] = new int[2];

        anchorView.getLocationOnScreen(authorLoc);
        final int anchorHeight = anchorView.getHeight();

        final int screenHeight = context.getResources().getDisplayMetrics().heightPixels;
        final int screenWidth = context.getResources().getDisplayMetrics().widthPixels;

        final int contentViewHeight = contentView.getMeasuredHeight();
        final int contentViewWidth = contentView.getMeasuredWidth();

        final boolean isNeedShowUp = (screenHeight - authorLoc[1] - anchorHeight < contentViewHeight);

        if (isNeedShowUp) {
            windowPos[0] = screenWidth - contentViewWidth;
            windowPos[1] = authorLoc[1] - contentViewHeight;
        } else {
            windowPos[0] = screenWidth - contentViewWidth;
            windowPos[1] = authorLoc[1] + anchorHeight;
        }
        return windowPos;
    }

    public static void showCallOrMessageWindow(Context context, View view, IntListener listener) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.popup_item_phone_message, null, false);
        View message = inflate.findViewById(R.id.pop_item_phone_message_message);
        View phone = inflate.findViewById(R.id.pop_item_phone_message_phone);
        PopupWindow pop = new PopupWindow(inflate, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        pop.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pop.setOutsideTouchable(true);
        pop.setTouchable(true);
        pop.setAnimationStyle(R.style.popupwindow_anim_phone);
        Window window = ((Activity) context).getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.alpha = 0.4f;
        window.setAttributes(layoutParams);
        pop.setOnDismissListener(() -> {
            layoutParams.alpha = 1f;
            window.setAttributes(layoutParams);
        });
        message.setOnClickListener(v -> {
            pop.dismiss();
            listener.getInt(0);
        });
        phone.setOnClickListener(v -> {
            pop.dismiss();
            listener.getInt(1);
        });
        pop.showAsDropDown(view);
    }

    public static void showBusinessFile(Context context, View view, View.OnClickListener listener) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.popup_business_file, null, false);

        View clickView = inflate.findViewById(R.id.popup_business_file_cl);

        PopupWindow pop = new PopupWindow(inflate, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        pop.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pop.setOutsideTouchable(true);
        pop.setTouchable(true);
        pop.setAnimationStyle(R.style.popupwindow_anim_notice);
        Window window = ((Activity) context).getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.alpha = 0.4f;
        window.setAttributes(layoutParams);
        pop.setOnDismissListener(() -> {
            layoutParams.alpha = 1f;
            window.setAttributes(layoutParams);
        });
        clickView.setOnClickListener(v -> {
            pop.dismiss();
            listener.onClick(v);
        });
        inflate.setOnClickListener(v -> pop.dismiss());
        pop.showAsDropDown(view);
    }

    public static void showUserBack(Context context, UserBackListener listener) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_back, null);
        EditText etText = view.findViewById(R.id.user_back_text);
        EditText etEmail = view.findViewById(R.id.user_back_email);

        MaterialDialog.Builder builder = new MaterialDialog.Builder(context);
        builder.autoDismiss(false);
        builder.title(context.getString(R.string.user_back));
        builder.positiveText(R.string.upload);
        builder.negativeText(R.string.cancel);
        builder.positiveColorRes(R.color.colorBlack);
        builder.negativeColorRes(R.color.colorBlack);
        builder.customView(view, false);
        builder.onNegative((dialog, which) -> dialog.dismiss());
        builder.onPositive((dialog, which) -> {
            if (!TextUtils.isEmpty(etText.getText().toString().trim())) {
                if (listener != null) {
                    listener.back(etText.getText().toString().trim(), etEmail.getText().toString().trim());
                }
                dialog.dismiss();
            } else {
                Toast.makeText(context, context.getString(R.string.enter_your_back), Toast.LENGTH_SHORT).show();
            }
        });
        builder.build().show();
    }

    public interface UserBackListener {
        void back(String text, String email);
    }

    public static void showOneLineCard(Context context, String content, String positiveText, String negativeText, MaterialDialog.SingleButtonCallback positiveListener, MaterialDialog.SingleButtonCallback negativeListener) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context);
        builder.positiveText(positiveText);
        builder.negativeText(negativeText);
        builder.positiveColorRes(R.color.colorBlack);
        builder.negativeColorRes(R.color.colorBlack);
        builder.content(content);
        builder.onNegative((dialog, which) -> {
            dialog.dismiss();
            negativeListener.onClick(dialog, which);
        });
        builder.onPositive((dialog, which) -> {
            dialog.dismiss();
            positiveListener.onClick(dialog, which);
        });
        builder.build().show();
    }

    public static void showMatchRecordPopupWindow(Context context, View view, StringListener listener) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.popup_item_match_record, null, false);

        LinearLayout llAddFriend = inflate.findViewById(R.id.popup_item_match_record_add_friend);
        LinearLayout llUploadAddressBook = inflate.findViewById(R.id.popup_item_match_record_upload_address_book);
        LinearLayout llScanBusinessCard = inflate.findViewById(R.id.popup_item_match_record_scanning_business_card);
        LinearLayout llScanQRCode = inflate.findViewById(R.id.popup_item_match_record_scanning_qr_code);
        LinearLayout llMyQRCode = inflate.findViewById(R.id.popup_item_match_record_my_qr_code);

        PopupWindow pop = new PopupWindow(inflate, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        pop.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pop.setOutsideTouchable(true);
        pop.setTouchable(true);
        pop.setAnimationStyle(R.style.popupwindow_anim_notice);
        Window window = ((Activity) context).getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.alpha = 0.4f;
        window.setAttributes(layoutParams);
        pop.setOnDismissListener(() -> {
            layoutParams.alpha = 1f;
            window.setAttributes(layoutParams);
        });
        llAddFriend.setOnClickListener(v -> {
            if (listener != null) {
                listener.getStrings(context.getString(R.string.TYPE_0));
                pop.dismiss();
            }
        });
        llUploadAddressBook.setOnClickListener(v -> {
            if (listener != null) {
                listener.getStrings(context.getString(R.string.TYPE_1));
                pop.dismiss();
            }
        });
        llScanBusinessCard.setOnClickListener(v -> {
            if (listener != null) {
                listener.getStrings(context.getString(R.string.TYPE_2));
                pop.dismiss();
            }
        });
        llScanQRCode.setOnClickListener(v -> {
            if (listener != null) {
                listener.getStrings(context.getString(R.string.TYPE_3));
                pop.dismiss();
            }
        });
        llMyQRCode.setOnClickListener(v -> {
            if (listener != null) {
                listener.getStrings(context.getString(R.string.TYPE_4));
                pop.dismiss();
            }
        });
        pop.showAsDropDown(view);
    }
}
