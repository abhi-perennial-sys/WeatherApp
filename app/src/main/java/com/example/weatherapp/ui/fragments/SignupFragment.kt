package com.example.weatherapp.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentSignupBinding
import com.example.weatherapp.models.Result
import com.example.weatherapp.ui.LoginActivity
import com.example.weatherapp.utils.showSnackBar
import com.example.weatherapp.viewmodels.LoginViewModel
import com.google.android.material.textfield.TextInputLayout

class SignupFragment : Fragment(), OnClickListener {

    private lateinit var binding: FragmentSignupBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DataBindingUtil.inflate<FragmentSignupBinding>(
            inflater,
            R.layout.fragment_signup, container, false
        ).also {
            binding = it
            return binding.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as LoginActivity).viewModel
        binding.viewModel = viewModel
        binding.signupButton.setOnClickListener(this)

        binding.name.addTextChangedListener(textWatcher)
        binding.username.addTextChangedListener(textWatcher)
        binding.password.addTextChangedListener(textWatcher)

        setSpans()
        subscribeUi()
    }

    private fun subscribeUi() {
        viewModel.loginLiveData.observe(viewLifecycleOwner) { result ->
            //hide loader
            viewModel.loader.set(false)
            viewModel.isButtonEnabled.set(true)
            result.let {
                if (result is Result.Success) {
                    viewModel.resetLoginLiveData()
                    NavHostFragment.findNavController(this)
                        .popBackStack(R.id.loginFragment, false)
                } else if (result is Result.Error) {
                    result.errorMsg.showSnackBar(binding.root)
                }
            }
        }
    }

    override fun onClick(p0: View?) {
        viewModel.signupUser(
            binding.name.text.toString().trim(),
            binding.username.text.toString().trim(),
            binding.password.text.toString().trim()
        )
    }

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            viewModel.nameError.set(null)
            viewModel.usernameError.set(null)
            viewModel.pwdError.set(null)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding.unbind()
    }


    private fun setSpans() {
        val startIndex = resources.getInteger(R.integer.login_start_index)
        val endIndex = resources.getInteger(R.integer.login_end_index)
        val spannableString = SpannableString(getString(R.string.already_have_acc))
        spannableString.setSpan(
            UnderlineSpan(),
            startIndex,
            endIndex,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )

        spannableString.setSpan(
            object : ClickableSpan() {
                override fun onClick(p0: View) {
                    p0.invalidate()
                    viewModel.resetLoginLiveData()
                    NavHostFragment.findNavController(this@SignupFragment)
                        .popBackStack(R.id.loginFragment, false)

                }
            },
            startIndex,
            endIndex,
            0
        )

        spannableString.setSpan(
            ForegroundColorSpan(Color.BLUE),
            startIndex,
            endIndex,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )

        binding.dontHaveAccount.text = spannableString
        binding.dontHaveAccount.movementMethod = LinkMovementMethod.getInstance()
    }

}