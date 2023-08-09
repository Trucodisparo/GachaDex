package com.keepcoding.gachadex.presentation.mainmenu

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keepcoding.gachadex.domain.usecase.GetCurrentSettingsUseCase
import com.keepcoding.gachadex.domain.usecase.GetLastEncounterUseCase
import com.keepcoding.gachadex.domain.usecase.SetCurrentSettingsUseCase
import com.keepcoding.gachadex.domain.usecase.SetLastEncounterUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

class MainMenuViewModel(
    private val getLastEncounterUseCase: GetLastEncounterUseCase,
    private val setLastEncounterUseCase: SetLastEncounterUseCase,
): ViewModel() {

    private var countDownTimer: CountDownTimer? = null

    private var _timeElapsed = MutableStateFlow(0L)
    private val timeElapsed: StateFlow<Long> get() = _timeElapsed

    private var _countDown = MutableStateFlow("Encounter")
    val countDown: StateFlow<String> get() = _countDown

    private val coolDownValue = 20000L //1h

    init{
        getLastEncounter()
        startTimer()
    }

    fun setLastEncounter(){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                setLastEncounterUseCase.invoke()
            }
            startTimer()
        }
    }

    fun getLastEncounter(){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                getLastEncounterUseCase.invoke().collect{
                    _timeElapsed.value = coolDownValue - (System.currentTimeMillis() - it)
                }
            }
        }
    }

     fun startTimer(){
         countDownTimer = object: CountDownTimer(timeElapsed.value, 1000){
            override fun onTick(time: Long) {
                _countDown.value = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(time),
                    TimeUnit.MILLISECONDS.toSeconds(time) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time)))
            }

            override fun onFinish() {
                _countDown.value = "Encounter"
            }
        }.start()
    }
}