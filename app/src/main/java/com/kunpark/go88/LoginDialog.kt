package com.kunpark.go88

import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.RelativeLayout
import com.kunpark.gamec.login.LoginViewModel
import com.kunpark.gamec.model.User
import kotlinx.android.synthetic.main.dialog_login.*
import kotlinx.android.synthetic.main.dialog_login.btnLogin

class LoginDialog(private val error: String, context: Context, private val user: User,private val viewModel: LoginViewModel) : Dialog(context) {
    init {
        setCancelable(false)
        setContentView(R.layout.dialog_login)
        window?.setLayout(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT
        )
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)

        initViews()
    }

    private fun initViews() {

        btnClose?.setOnClickListener {
            dismiss()
        }

        btnLogin?.setOnClickListener {
            pb.visibility = View.VISIBLE
            val view: View = currentFocus?: return@setOnClickListener
            hideKeyBoard(context, view)
            
            if(checkLogin(etUserName.text.toString(), etPassword.text.toString()) == null) {
                viewModel.login(User(etUserName.text.toString(), etPassword.text.toString()))
            } else {
                pb.visibility = View.GONE
            }
        }

        csMainLogin?.setOnClickListener {
            val view: View = currentFocus?: return@setOnClickListener
            hideKeyBoard(context, view)
        }
    }

    private fun checkLogin(userName: String, password: String): String? {
        if(userName.isNullOrEmpty()) {
            return "Please enter username!"
        } else if(password.isNullOrEmpty()) {
            return "Please enter password!"
        }

        return null
    }

    private fun hideKeyBoard(context: Context, view: View) {
        try {
            val keyboard =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            keyboard.hideSoftInputFromWindow(view.windowToken, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}