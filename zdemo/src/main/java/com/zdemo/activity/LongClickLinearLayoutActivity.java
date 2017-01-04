package com.zdemo.activity;

import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.czt.mp3recorder.MP3Recorder;
import com.zbase.listener.OnActionDownLongListener;
import com.zbase.listener.OnActionUpListener;
import com.zbase.listener.OnTimeEndListener;
import com.zbase.util.PopUtil;
import com.zbase.view.LongClickLinearLayout;
import com.zbase.view.TimerTextView;
import com.zbase.view.popupwindow.AlphaPopupWindow;
import com.zdemo.R;

import java.io.File;
import java.io.IOException;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/12/15
 * 描述：长按布局控件结合录音播放计时等
 */
public class LongClickLinearLayoutActivity extends BaseActivity {

    private LongClickLinearLayout longClickLinearLayout;
    private TimerTextView timerTextView;
    private TextView tv_record;
    private AlphaPopupWindow recordPopupWindow;
    private AlphaPopupWindow playPopupWindow;
    private File mp3File;
    private MP3Recorder mRecorder;
    private View pw_record_view;
    private View pw_play_view;
    private AnimationDrawable recordAnimationDrawable;
    private AnimationDrawable playAnimationDrawable;
    private TimerTextView recordTimerTextView;
    private TimerTextView playTimerTextView;
    private Button btn_play;
    private Button btn_delete;
    private MediaPlayer mediaPlayer;

    @Override
    protected int inflateMainLayoutId() {
        return R.layout.activity_longclick_linearlayout;
    }

    @Override
    protected void initView(View view) {
        longClickLinearLayout = (LongClickLinearLayout) view.findViewById(R.id.longClickLinearLayout);
        timerTextView = (TimerTextView) view.findViewById(R.id.timerTextView);
        tv_record = (TextView) view.findViewById(R.id.tv_record);
        btn_play = (Button) view.findViewById(R.id.btn_play);
        btn_delete = (Button) view.findViewById(R.id.btn_delete);
    }

    @Override
    protected void setListener() {
        longClickLinearLayout.setOnActionDownLongListener(new OnActionDownLongListener() {
            @Override
            public void onActionDownLong(View v) {
                //开始录音
                tv_record.setText(R.string.loosen_the_stop);
                showRecordPopupWindow();
                mRecorder = new MP3Recorder(mp3File);
                try {
                    mRecorder.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        longClickLinearLayout.setOnActionUpListener(new OnActionUpListener() {
            @Override
            public void onActionUp(View v) {
                recordPopupWindow.dismiss();
            }
        });
        longClickLinearLayout.setOnTimeEndListener(new OnTimeEndListener() {
            @Override
            public void onTimeEnd() {
                PopUtil.toast(context, "已到达最大录音时间！");
                recordPopupWindow.dismiss();
            }
        });

        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timerTextView.start();
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timerTextView.pause();
            }
        });
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timerTextView.continueTo();
            }
        });
        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timerTextView.stop();
            }
        });
        btn_play.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
    }

    @Override
    protected void initValue() {
        mp3File = new File(Environment.getExternalStorageDirectory(), "record.mp3");
        pw_record_view = inflate(R.layout.inflate_pw_record);
        ImageView recordImageView = ((ImageView) pw_record_view.findViewById(R.id.iv_record));
        recordAnimationDrawable = (AnimationDrawable) recordImageView.getDrawable();
        recordTimerTextView = (TimerTextView) pw_record_view.findViewById(R.id.recordTimerTextView);

        pw_play_view = inflate(R.layout.inflate_pw_play);
        ImageView playImageView = ((ImageView) pw_play_view.findViewById(R.id.iv_play));
        playAnimationDrawable = (AnimationDrawable) playImageView.getDrawable();
        playTimerTextView = (TimerTextView) pw_play_view.findViewById(R.id.playTimerTextView);
    }

    private void showRecordPopupWindow() {
        if (recordPopupWindow == null) {
            recordPopupWindow = new AlphaPopupWindow(context, pw_record_view);
            recordPopupWindow.setDark(false);
            recordPopupWindow.setOutsideTouchNotDismissExceptKeyBack();
            recordPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    stopRecord();
                }
            });
        }
        recordPopupWindow.showAtCenter(this);
        recordAnimationDrawable.start();
        recordTimerTextView.start();
    }

    private void showPlayPopupWindow() {
        if (playPopupWindow == null) {
            playPopupWindow = new AlphaPopupWindow(context, pw_play_view);
            playPopupWindow.setDark(false);
            playPopupWindow.setOutsideTouchNotDismissExceptKeyBack();
            playPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    stopPlay();
                }
            });
        }
        playPopupWindow.showAtCenter(this);
        playAnimationDrawable.start();
        playTimerTextView.start();
    }

    private void stopRecord() {
        tv_record.setText(R.string.hold_down_the_talk);
        recordAnimationDrawable.stop();
        recordTimerTextView.stop();
        mRecorder.stop();
    }

    private void stopPlay() {
        playAnimationDrawable.stop();
        playTimerTextView.stop();
        mediaPlayer.stop();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_play:
                if (mp3File.exists()) {
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            playPopupWindow.dismiss();
                        }
                    });
                    try {
                        mediaPlayer.setDataSource(mp3File.getAbsolutePath());
                        mediaPlayer.prepare();
                        playTimerTextView.setMaxSecond(mediaPlayer.getDuration() / 1000);
                        showPlayPopupWindow();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mediaPlayer.start();
                } else {
                    PopUtil.toast(context, "mp3文件不存在！");
                }
                break;
            case R.id.btn_delete:
                if (mp3File.exists()) {
                    mp3File.delete();
                    PopUtil.toast(context, "mp3删除成功！");
                } else {
                    PopUtil.toast(context, "mp3文件不存在！");
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        longClickLinearLayout.onDestroy();
        timerTextView.onDestroy();
        recordTimerTextView.onDestroy();
        playTimerTextView.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
        mp3File.delete();
    }
}
