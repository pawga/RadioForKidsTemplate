package com.pawga.radio.ui.languages

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pawga.radio.R
import com.pawga.radio.common.IBinding
import com.pawga.radio.common.SelectRecyclerAdapter
import com.pawga.radio.databinding.LanguageItemBinding
import com.pawga.radio.extensions.getTitle
import com.pawga.radio.extensions.setLanguageImage
import com.pawga.radio.model.RadioLanguage
import kotlinx.android.synthetic.main.select_languages_fragment.*

class SelectLanguagesFragment : Fragment() {

    companion object {
        fun newInstance() = SelectLanguagesFragment()
    }

    private lateinit var viewModel: SelectLanguagesViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.select_languages_fragment, container, false)
        setHasOptionsMenu(true)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.languages_fragment_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.id_apply -> {
                viewModel.saveLanguages()
                findNavController().navigate(R.id.nav_home)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(SelectLanguagesViewModel::class.java)

        languages_list?.apply {
            val linearLayoutManager = LinearLayoutManager(context)
            linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
            layoutManager = linearLayoutManager
            setHasFixedSize(true)
            adapter = LanguageRecyclerAdapter(viewModel)
        }
    }

    internal class LanguageAdapterItem(private val binding: LanguageItemBinding) : IBinding<RadioLanguage> {
        override val root: View = binding.root
        override lateinit var item: RadioLanguage // important!!!
        override fun executePendingBindings() {
            binding.textViewLanguage.text = item.language.getTitle(binding.root.context)
            binding.imageButtonSelectLanguage.visibility = if (item.select) View.VISIBLE else View.INVISIBLE
            binding.imageViewLanguage.setLanguageImage(item)
        }
        override fun getSignVisibilityView(): View = binding.imageButtonSelectLanguage
    }

    internal class LanguageRecyclerAdapter(viewModel: SelectLanguagesViewModel) :
            SelectRecyclerAdapter<RadioLanguage, LanguageAdapterItem>(viewModel) {

        override val list = viewModel.list

        override fun from(parent: ViewGroup): SelectViewHolder<RadioLanguage, LanguageAdapterItem> {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = LanguageItemBinding.inflate(layoutInflater, parent, false)
            return LanguageViewHolder(LanguageAdapterItem(binding))
        }

        internal class LanguageViewHolder(binding: LanguageAdapterItem)
            : SelectViewHolder<RadioLanguage, LanguageAdapterItem>(binding)
    }
}