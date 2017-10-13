package com.luhuan.rxpicker;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;

import com.luhuan.rxpicker.bean.ImageItem;
import com.luhuan.rxpicker.ui.RxPickerActivity;
import com.luhuan.rxpicker.ui.fragment.ResultHandlerFragment;
import com.luhuan.rxpicker.utils.PickerConfig;
import com.luhuan.rxpicker.utils.RxPickerImageLoader;
import com.luhuan.rxpicker.utils.RxPickerManager;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * @author Smile
 * @time 2017/4/18  下午6:01
 * @desc ${TODD}
 */
public class RxPicker {

    private RxPicker(PickerConfig config) {
        RxPickerManager.getInstance().setConfig(config);
    }


    /**
     * init RxPicker
     */
    public static void init(RxPickerImageLoader imageLoader) {
        RxPickerManager.getInstance().init(imageLoader);
    }


    /**
     * Using the custom config
     */

    static RxPicker of(PickerConfig config) {
        return new RxPicker(config);
    }

    /**
     * Using the default config
     */
    public static RxPicker of() {
        return new RxPicker(new PickerConfig());
    }

    /**
     * Set the selection mode
     */
    public RxPicker single(boolean single) {
        RxPickerManager.getInstance().setMode(single ? PickerConfig.SINGLE_IMG : PickerConfig.MULTIPLE_IMG);
        return this;
    }

    /**
     * Set the show  Taking pictures;
     */
    public RxPicker camera(boolean showCamera) {
        RxPickerManager.getInstance().showCamera(showCamera);
        return this;
    }

    /**
     * Set the select  image limit
     */
    public RxPicker limit(int minValue, int maxValue) {
        RxPickerManager.getInstance().limit(minValue, maxValue);
        return this;
    }

    /**
     * start picker from activity
     */
    public Observable<List<ImageItem>> start(Activity activity) {
        return start(activity.getFragmentManager());
    }

    /**
     * start picker from fragment
     */
    public Observable<List<ImageItem>> start(Fragment fragment) {
        return start(fragment.getFragmentManager());
    }

    /**
     * start picker from fragment
     */
    private Observable<List<ImageItem>> start(FragmentManager fragmentManager) {
        ResultHandlerFragment fragment = (ResultHandlerFragment) fragmentManager.findFragmentByTag(ResultHandlerFragment.class.getSimpleName());
        if (fragment == null) {
            fragment = ResultHandlerFragment.newInstance();
            fragmentManager.beginTransaction().add(fragment, fragment.getClass().getSimpleName()).commit();
        } else if (fragment.isDetached()) {
            fragmentManager.beginTransaction().attach(fragment).commit();
        }
        return getListItem(fragment);
    }


    private Observable<List<ImageItem>> getListItem(final ResultHandlerFragment finalFragment) {
        return finalFragment.getAttachSubject().filter(new Func1<Boolean, Boolean>() {
            @Override
            public Boolean call(Boolean aBoolean) {
                return aBoolean;
            }
        }).flatMap(new Func1<Boolean, Observable<List<ImageItem>>>() {
            @Override
            public Observable<List<ImageItem>> call(Boolean aBoolean) {
                Intent intent = new Intent(finalFragment.getActivity(), RxPickerActivity.class);
                finalFragment.startActivityForResult(intent, ResultHandlerFragment.REQUEST_CODE);
                return finalFragment.getResultSubject();
            }
        }).take(1);
    }


}
