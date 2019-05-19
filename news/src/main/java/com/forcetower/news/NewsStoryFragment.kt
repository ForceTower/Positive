package com.forcetower.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.forcetower.news.databinding.FragmentNewsHistoryBinding
import android.view.MotionEvent
import com.forcetower.storiesprogressview.StoriesProgressView

class NewsStoryFragment : Fragment(), StoriesProgressView.StoriesListener {
    private lateinit var binding: FragmentNewsHistoryBinding
    private var pressTime = 0L
    private val limit = 500L
    private var index = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentNewsHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.storiesProgress.apply {
            setStoriesCount(stories.size)
            setStoryDuration(4000L)
            setStoriesListener(this@NewsStoryFragment)
            startStories()
        }

        binding.news = stories[0]
        binding.executePendingBindings()

        binding.next.apply {
            setOnClickListener { binding.storiesProgress.skip() }
            setOnTouchListener(onTouchListener)
        }

        binding.prev.apply {
            setOnClickListener { binding.storiesProgress.reverse() }
            setOnTouchListener(onTouchListener)
        }
    }

    override fun onNext() {
        if (index + 1 >= stories.size) return
        binding.news = stories[++index]
    }

    override fun onPrev() {
        if (index - 1 < 0) return
        binding.news = stories[--index]
    }

    override fun onComplete() {

    }

    override fun onDestroy() {
        binding.storiesProgress.destroy()
        super.onDestroy()
    }

    private val onTouchListener = View.OnTouchListener { _, event ->
        return@OnTouchListener when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                pressTime = System.currentTimeMillis()
                binding.storiesProgress.pause()
                false
            }
            MotionEvent.ACTION_UP -> {
                val now = System.currentTimeMillis()
                binding.storiesProgress.resume()
                limit < now - pressTime
            }
            else -> false
        }
    }

    private val stories = listOf(
            News("Pacientes do Programa do Glaucoma reclamam das longas filas para pegar colírio em Maceió", "G1"),
            News("Sesi e Senai ofertas oficinas e palestras gratuitas na Semana da Educação", "G1"),
            News("Operação combate grupo suspeito de extorquir e ameaçar moradores de condomínios em Pelotas", "G1"),
            News("Procissão que homenageia São Jorge e Ogum completa 50 anos", "G1"),
            News("Após conhecer mulher em aplicativo de relacionamentos, homem é suspeito de vender carro dela e fugir com dinheiro", "G1"),
            News("Dia de São Jorge tem missas e procissão luminosa em Porto Alegre", "G1"),
            News("Veja o convite para palestra de Mourão nos EUA que irritou Carlos Bolsonaro", "O Globo"),
            News("Carro derruba poste e queda provoca congestionamento na BR-267, em Poços de Caldas", "G1"),
            News("Agricultores têm dificuldades para criar peixes em viveiros construídos há 15 anos por falta de títulos das terras em MT", "G1"),
            News("Grupo realiza protesto na BR-104 para pedir instalação de lombadas", "UOL"),
            News("Cadastro Positivo pode se estender a 89% da população adulta do Paraná", "UOL"),
            News("Poste cai e destrói parte da fachada de  casarão no Centro Histórico de Cuiabá", "G1"),
            News("Partidos tentam derrubar sigilo de pareceres da reforma na Justiça", "UOL"),
            News("Confira as vagas de estágio disponíveis nesta terça (23) em Petrolina", "G1"),
            News("Estudo mostra evidências do que faz os cegos terem uma audição mais sensível", "UOL"),
            News("Prefeitura apoia lançamento de livro em Bertioga", "G1"),
            News("Explosões no Sri Lanka: o desconhecido grupo islâmico apontado como responsável pelos piores ataques desde 11 de setembro", "G1"),
            News("VÍDEOS: MG2 desta segunda, 22 de abril", "G1"),
            News("Após governo ceder, CCJ volta a tentar votar reforma da Previdência nesta terça", "UOL"),
            News("Governador da Paraíba sanciona lei que autoriza a extinção da Empasa", "G1"),
            News("Segundo episódio de Game of Thrones vazou antes da estreia oficial", "UOL"),
            News("Júnior saiu para comer hamburger e minha mãe não gostou", "TretaNews"),
            News("A Gabriela é grande e magra! Disse o cidadão anonimo enquanto era esmagado pela garota", "Assis Zuero"),
            News("As pessoas que jogam FreeFire perdem mais tempo com o jogo do que com outras coisas interessantes, mostra estudo", "RiotGames"),
            News("Os atores de Game of Thrones se mostram desapontados com o rumo que a série esta tomando", "Warner"),
            News("A Xiaomi lança oficialmente sua coleção outono/inverno de guarda-chuvas high tech", "Xiaomi"),
            News("Após o quão ruim estava a série, George R. R. Martin inicia o KickStarter para produzir Game of Thrones Brotherhood", "FOX"),
            News("A Disney adquiriu hoje os direitos de produção da DC Comics. Eles prometem um mundo compartilhado de Superman e Capitã Marvel", "Esquina")
    )
}