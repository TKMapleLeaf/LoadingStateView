package com.caisl.loadinghelper.sample.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.caisl.loadinghelper.LoadingHelper;
import com.caisl.loadinghelper.sample.R;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

/**
 * @author caisl
 * @since 2019/5/18
 */
public class TitleAdapter extends LoadingHelper.Adapter<TitleAdapter.TitleViewHolder> {

  private TitleConfig mConfig;

  public TitleAdapter() {
    this(new TitleConfig());
  }

  /**
   * add title and back button
   * @param title title text
   * @param type back button type
   */
  public TitleAdapter(String title,TitleConfig.Type type) {
    this(new TitleConfig(title,type));
  }

  /**
   * add right button
   * @param rightText right button text
   * @param rightClickTask right button click task
   */
  public TitleAdapter(String rightText, Runnable rightClickTask) {
    this(new TitleConfig(rightText,rightClickTask));
  }

  public TitleAdapter(TitleConfig config) {
    mConfig = config;
  }

  @NonNull
  @Override
  public TitleAdapter.TitleViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
    return new TitleViewHolder(inflater.inflate(R.layout.loading_layout_title,parent,false));
  }

  @Override
  public void onBindViewHolder(@NonNull final TitleAdapter.TitleViewHolder holder) {
    if (!TextUtils.isEmpty(mConfig.getTitleText())){
      holder.setTitleText(mConfig.getTitleText());
    }
    if (mConfig.getType() == TitleConfig.Type.BACK){
      holder.addBackBtn();
    }else {
      holder.hideBackBtn();
    }
    if (!TextUtils.isEmpty(mConfig.getRightText())&&mConfig.getRightClickTask()!=null){
      holder.addRightBtn(mConfig.getRightText(),mConfig.getRightClickTask());
    }
  }

  class TitleViewHolder extends LoadingHelper.ViewHolder {

    private final CommonTitleBar mTitleBar;

    TitleViewHolder(@NonNull View rootView) {
      super(rootView);
      mTitleBar = (CommonTitleBar) rootView;
      mTitleBar.setListener(new CommonTitleBar.OnTitleBarListener() {
        @Override
        public void onClicked(View v, int action, String extra) {

        }
      });
    }

    void addBackBtn() {
      mTitleBar.getLeftImageButton().setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          ((Activity)rootView.getContext()).finish();
        }
      });
    }

    void hideBackBtn() {
      mTitleBar.getLeftImageButton().setImageDrawable(null);
    }

    void setTitleText(String title) {
      mTitleBar.getCenterTextView().setText(title);
    }

    void addRightBtn(String rightText, final Runnable rightClickTask) {
      mTitleBar.getRightTextView().setText(rightText);
      mTitleBar.getRightTextView().setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          rightClickTask.run();
        }
      });
    }
  }
}
