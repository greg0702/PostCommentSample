package my.com.postcommentsample.base

import android.widget.Toast
import androidx.annotation.LayoutRes
import my.com.postcommentsample.base.mvp.BaseMvp
import my.com.postcommentsample.base.mvp.presenter.BasePresenter
import my.com.postcommentsample.widget.LoadingDialog
import net.grandcentrix.thirtyinch.TiActivity

abstract class BaseActivity<V : BaseMvp.FirstView, P : BasePresenter<V>> : TiActivity<P, V>(), BaseMvp.FirstView {

    private var loadingDialog: LoadingDialog? = null

    @LayoutRes
    protected abstract fun layout(): Int

    override fun showLoading(msg: String) {
        if (!isFinishing){
            hideLoading()
            loadingDialog = LoadingDialog().newInstance(msg, false)
            loadingDialog!!.show(supportFragmentManager, "PROGRESS_DIALOG")
        }
    }

    override fun hideLoading() { loadingDialog?.dismiss() }

    override fun showToast(msg: String) { Toast.makeText(this, msg, Toast.LENGTH_SHORT).show() }

}