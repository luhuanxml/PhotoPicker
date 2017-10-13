package com.caimuhao.rxpicker.base;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * @author Smile
 * @time 2017/4/19  上午9:30
 * @desc ${TODD}
 */
public abstract class BasePresenter<V extends BaseView> {

  public V view;
  private CompositeSubscription compositeDisposable = new CompositeSubscription();

  protected void start() {

  }

  void attachModelView(V v) {
    this.view = v;
    start();
  }

  void detachView() {
    this.view = null;
    if (compositeDisposable != null && compositeDisposable.isUnsubscribed()) {
      compositeDisposable.unsubscribe();
    }
  }

  public Subscription add(Subscription disposable) {
    compositeDisposable.add(disposable);
    return disposable;
  }
}