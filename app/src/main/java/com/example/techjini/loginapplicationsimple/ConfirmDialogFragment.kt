package com.example.techjini.loginapplicationsimple

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.*
import com.example.techjini.loginapplicationsimple.databinding.FragmentDialogConfirmationBinding

/**
 * Created by Surya N on 03/08/18.
 */
class ConfirmDialogFragment : DialogFragment() {

    private var binding: FragmentDialogConfirmationBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dialog_confirmation, container, false)
        binding?.setClickHandler { dismissDialog() }
        return binding?.root
    }

    override fun onResume() {
        super.onResume()
        val params = dialog.window?.attributes
        params?.dimAmount = 0.3f
        params?.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
        params?.width = ViewGroup.LayoutParams.MATCH_PARENT
        params?.height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialog.window?.setGravity(Gravity.CENTER)
        dialog.window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        dialog.window?.attributes = params as android.view.WindowManager.LayoutParams
    }


    private fun dismissDialog() {
        if (dialog != null) {
            dialog.dismiss()
        }
    }

}
