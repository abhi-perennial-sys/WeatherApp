package com.example.weatherapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.weatherapp.R
import com.example.weatherapp.data.AppPreferences
import com.example.weatherapp.databinding.FragmentLoginBinding
import com.example.weatherapp.models.Result
import com.example.weatherapp.ui.HomeActivity
import com.example.weatherapp.ui.LoginActivity
import com.example.weatherapp.utils.showSnackBar
import com.example.weatherapp.viewmodels.LoginViewModel
import com.google.android.material.textfield.TextInputLayout

class LoginFragment : Fragment(), OnClickListener {

    lateinit var binding: FragmentLoginBinding
    lateinit var viewModel: LoginViewModel
    lateinit var username: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DataBindingUtil.inflate<FragmentLoginBinding?>(
            inflater, R.layout.fragment_login, container, false
        ).also {
            binding = it
            return it.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //shared viewModel
        viewModel = (activity as LoginActivity).viewModel
        binding.viewModel = viewModel

        //added clickListener and textWatcher
        binding.loginButton.setOnClickListener(this)
        binding.username.addTextChangedListener(textWatcher)
        binding.password.addTextChangedListener(textWatcher)

        setSpans()
        //subscribe livedata objects to observe data and update ui
        subscribeUi()
    }

    override fun onClick(p0: View?) {
        username = binding.username.text.toString().trim()
        viewModel.loginUser(
            username,
            binding.password.text.toString().trim()
        )
    }

    private fun subscribeUi() {
        viewModel.loginLiveData.observe(viewLifecycleOwner) { result ->
            //hide loader
            viewModel.loader.set(false)
            viewModel.isButtonEnabled.set(true)
            result.let {
                if (result is Result.Success) {
                    //login success
                    AppPreferences(requireContext()).setUserLoggedIn(true)
                    AppPreferences(requireContext()).setLoggedInUsername(username)
                    startActivity(Intent(requireActivity(), HomeActivity::class.java))
                } else if (result is Result.Error) {
                    result.errorMsg.showSnackBar(binding.root)
                }
            }
        }
    }

    private fun setSpans() {

        val startIndex = resources.getInteger(R.integer.dont_have_acc_start)
        val endIndex = resources.getInteger(R.integer.dont_have_acc_end)
        val spannableString = SpannableString(getString(R.string.dont_have_account))

        //setting underline span
        spannableString.setSpan(
            UnderlineSpan(),
            startIndex,
            endIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        //setting clickable Span
        spannableString.setSpan(
            object : ClickableSpan() {
                override fun onClick(p0: View) {
                    p0.invalidate()
                    viewModel.resetLoginLiveData()
                    NavHostFragment.findNavController(this@LoginFragment)
                        .navigate(R.id.signupFragment)
                }
            },
            startIndex,
            endIndex,
            0
        )

        //setting foreground span
        spannableString.setSpan(
            ForegroundColorSpan(ResourcesCompat.getColor(resources, R.color.purple_500, null)),
            startIndex,
            endIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.dontHaveAccount.text = spannableString
        binding.dontHaveAccount.movementMethod = LinkMovementMethod.getInstance()
    }

    //textWatcher to remove errors
    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            viewModel.usernameError.set(null)
            viewModel.pwdError.set(null)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.unbind()
    }


}