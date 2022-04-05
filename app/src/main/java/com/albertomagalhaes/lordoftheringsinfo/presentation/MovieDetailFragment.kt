package com.albertomagalhaes.lordoftheringsinfo.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.albertomagalhaes.lordoftheringsinfo.R
import com.albertomagalhaes.lordoftheringsinfo.databinding.FragmentMovieDetailBinding
import com.albertomagalhaes.lordoftheringsinfo.databinding.ItemCharacterBinding
import com.albertomagalhaes.lordoftheringsinfo.databinding.ItemQuoteBinding
import com.albertomagalhaes.lordoftheringsinfo.domain.model.CharacterModel
import com.albertomagalhaes.lordoftheringsinfo.domain.model.MovieModel
import com.albertomagalhaes.lordoftheringsinfo.domain.model.QuoteModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MovieDetailFragment : Fragment() {

    lateinit var binding: FragmentMovieDetailBinding
    private val viewModel by sharedViewModel<AppViewModel>()

    private lateinit var movieSelected: MovieModel
    private lateinit var characterList: List<CharacterModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieSelected = viewModel.movieSelected
        characterList = viewModel.characterListByMovie
        setupListeners()
        setupCharacterList()
    }

    private fun setupListeners(){
        binding.btnBack.setOnClickListener {
            viewModel.redirectToMovieList()
        }
    }

    private fun setupCharacterList(){
        binding.apply {
            tvMovieTitle.text = movieSelected.name
            handleVisibility(characterList)
            setupRecyclerView(characterList)
        }
    }

    private fun FragmentMovieDetailBinding.handleVisibility(
        characterList: List<CharacterModel>
    ){
        if(characterList.isEmpty()){
            ivLogo.setInvisible()
            rvCharacterList.show(false)
            ivEmptyState.show(true)
        }
    }

    private fun setupRecyclerView(
        characterList: List<CharacterModel>
    ){
        binding.rvCharacterList.adapter = SingleTypeGenericAdapter(
            ItemCharacterBinding::inflate,
            characterList
        ){ item, charBinding, _, _ ->
            charBinding.apply {
                setupItemList(item)
                setupContainerExpanding(item)
            }
        }
    }

    private fun ItemCharacterBinding.setupItemList(
        item: CharacterModel
    ){
        tvCharacterName.text = item.name
        val genderDrawable =
            when(item.gender?.trim()?.lowercase()){
                "female" -> ContextCompat.getDrawable(requireContext(), R.drawable.ic_female)
                "male" -> ContextCompat.getDrawable(requireContext(), R.drawable.ic_male)
                else -> ContextCompat.getDrawable(requireContext(), R.drawable.ic_interrogation)
            }
        ivGenderIcon.setImageDrawable(genderDrawable)
    }

    private fun ItemCharacterBinding.setupContainerExpanding(
        character: CharacterModel
    ){
        restoreState(character)
        clFixedContainer.setOnClickListener {
            viewModel.getCharacterQuotesByMovie(
                movieSelected.id,
                character.id
            ){
                setupCharacterInfo(character)
                setupQuotesAdapter(it.orEmpty())
                changeState(character)
            }
        }
    }

    private fun ItemCharacterBinding.restoreState(character: CharacterModel){
        collapsing()
        character.showQuotes = false
    }

    private fun ItemCharacterBinding.changeState(character: CharacterModel){
        if(character.showQuotes) collapsing()
        else expanding()
        character.showQuotes = !character.showQuotes
    }

    private fun ItemCharacterBinding.collapsing(){
        ivExpandIcon.rotate(0f)
        clExpandableContainer.collapse()
    }

    private fun ItemCharacterBinding.expanding(){
        ivExpandIcon.rotate(180f)
        clExpandableContainer.expand()
    }

    private fun ItemCharacterBinding.setupCharacterInfo(character: CharacterModel){
        character.apply {
            var counting = 0
            tvCharacterSpouse.setTextOrHide(spouse).let {
                tvCharacterSpouseLabel.show(it)
                counting += if(it) 1 else 0
            }
            tvCharacterRace.setTextOrHide(race).let {
                tvCharacterRaceLabel.show(it)
                counting += if(it) 1 else 0
            }
            if(wikiUrl.isNullOrBlank()){
                clKnowMore.show(false)
            }else{
                counting += 1
                clKnowMore.onClickRedirect(wikiUrl)
            }
            clCharacterInfo.show(counting != 0)
        }
    }

    private fun ItemCharacterBinding.setupQuotesAdapter(
        quotes: List<QuoteModel>
    ){
        rvQuoteList.adapter = SingleTypeGenericAdapter(
            ItemQuoteBinding::inflate,
            quotes
        ){ item, quoteBinding, _, _ ->
            quoteBinding.apply {
                tvQuote.text = item.dialog
            }
        }
    }

}