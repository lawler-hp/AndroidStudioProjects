package com.example.servicebestpractice;

/**
 * Okhttp的回调接口
 */
public interface DownloadListener {
    /**
     * 通知当前下载进度
     * @param progress
     */
    public abstract void onProgress(int progress);

    /**
     * 通知下载成功
     */
    public abstract void onSuccess();
    public abstract void onFailed();
    //下载暂停
    public abstract void onPaused();
    //下载取消
    public abstract void onCanceled();

}
