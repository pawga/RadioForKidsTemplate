package com.pawga.radio.ui.theme

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.pawga.radio.R
import com.pawga.radio.common.IBinding
import com.pawga.radio.common.SelectRecyclerAdapter
import com.pawga.radio.databinding.ThemeItemBinding
import com.pawga.radio.extensions.getTitle
import com.pawga.radio.extensions.setThemeImage
import com.pawga.radio.model.RadioTheme
import com.pawga.radio.ui.RadioforChildrenActivity
import kotlinx.android.synthetic.main.select_theme_fragment.*

class SelectThemeFragment : Fragment() {

    companion object {
        fun newInstance() = SelectThemeFragment()
    }

    private lateinit var viewModel: SelectThemeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.select_theme_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SelectThemeViewModel::class.java)

        themes_list?.apply {
            val linearLayoutManager = LinearLayoutManager(context)
            linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
            layoutManager = linearLayoutManager
            setHasFixedSize(true)
            adapter = ThemeRecyclerAdapter(viewModel)
        }

        viewModel.savedNewTheme.observe(this, Observer {
            if (it) {
                if (activity != null) {
                    val newIntent = Intent(activity, RadioforChildrenActivity::class.java)
                    startActivity(newIntent)
                    activity?.finish()
                }
            }
        })
    }

    internal class ThemeAdapterItem(private val binding: ThemeItemBinding) : IBinding<RadioTheme> {
        override val root: View = binding.root
        override lateinit var item: RadioTheme // important!!!
        override fun executePendingBindings() {
            binding.themeTextView.text = item.theme.getTitle(binding.root.context)
            binding.themeSelectImageButton.visibility = if (item.select) View.VISIBLE else View.INVISIBLE
            binding.themeImageView.setThemeImage(item)
        }
        override fun getSignVisibilityView(): View = binding.themeSelectImageButton
    }


    internal class ThemeRecyclerAdapter(val viewModel: SelectThemeViewModel) :
            SelectRecyclerAdapter<RadioTheme, ThemeAdapterItem>(viewModel) {

        override val list = viewModel.list

        override fun from(parent: ViewGroup): SelectViewHolder<RadioTheme, ThemeAdapterItem> {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ThemeItemBinding.inflate(layoutInflater, parent, false)
            return ThemeViewHolder(ThemeAdapterItem(binding))
        }

        override fun onBindViewHolder(holder: SelectViewHolder<RadioTheme, ThemeAdapterItem>, position: Int) {
            super.onBindViewHolder(holder, position)

            val item = list[position]

            holder.binding.root.setOnClickListener {
                viewModel.saveTheme(item.theme)
            }
        }

        internal class ThemeViewHolder(binding: ThemeAdapterItem)
            : SelectViewHolder<RadioTheme, ThemeAdapterItem>(binding)
    }
}
