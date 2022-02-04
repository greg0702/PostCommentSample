@file:Suppress("DEPRECATION")

package my.com.postcommentsample.widget

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class LoadingDialog : DialogFragment() {

    private val getMsg = ""
    private var loadingDialog: ProgressDialog? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val acMsg = arguments?.getString(getMsg)

        loadingDialog = ProgressDialog(activity)

        loadingDialog!!.setMessage(acMsg)

        return loadingDialog as ProgressDialog

    }

    override fun onAttach(context: Context) { super.onAttach(context) }

    fun newInstance(msg: String?, cancellable: Boolean): LoadingDialog {
        val args = Bundle()
        args.putString(getMsg, msg)
        val loadingDialogView = LoadingDialog()
        loadingDialogView.isCancelable = cancellable
        loadingDialogView.arguments = args
        return loadingDialogView
    }

    fun setMessage(msg: String) {
        loadingDialog?.setMessage(msg)
    }

}