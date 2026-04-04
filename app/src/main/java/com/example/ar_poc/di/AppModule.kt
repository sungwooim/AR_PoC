package com.example.ar_poc.di

import android.content.Context
import com.example.ar_poc.ai.GeminiClient
import com.example.ar_poc.data.audio.TTSManager
import com.example.ar_poc.data.discovery.DiscoveryTracker
import com.example.ar_poc.data.heritage.HeritageKnowledgeSource
import com.example.ar_poc.data.heritage.LocalHeritageKnowledgeSource
import com.example.ar_poc.data.location.ARSensorProvider
import com.example.ar_poc.data.location.LocationProviderImpl
import com.example.ar_poc.data.location.OutdoorSpatialProvider
import com.example.ar_poc.data.quiz.QuizRepository
import com.example.ar_poc.domain.HeritageRecognitionPipeline
import com.example.ar_poc.domain.location.LocationProvider
import com.example.ar_poc.domain.repository.HeritageRepository
import com.example.ar_poc.domain.spatial.SpatialContextProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGeminiClient(): GeminiClient = GeminiClient()

    @Provides
    @Singleton
    fun provideARSensorProvider(@ApplicationContext context: Context): ARSensorProvider =
        ARSensorProvider(context)

    @Provides
    @Singleton
    fun provideLocationProvider(
        @ApplicationContext context: Context,
        devModeManager: DevModeManager
    ): LocationProvider = LocationProviderImpl(context) { devModeManager.isDevMode }

    @Provides
    @Singleton
    fun provideHeritageKnowledgeSource(): HeritageKnowledgeSource = LocalHeritageKnowledgeSource()

    @Provides
    @Singleton
    fun provideHeritageRecognitionPipeline(
        geminiClient: GeminiClient,
        locationProvider: LocationProvider,
        knowledgeSource: HeritageKnowledgeSource
    ): HeritageRecognitionPipeline = HeritageRecognitionPipeline(geminiClient, locationProvider, knowledgeSource)

    @Provides
    @Singleton
    fun provideHeritageRepository(
        knowledgeSource: HeritageKnowledgeSource
    ): HeritageRepository = HeritageRepository(knowledgeSource)

    @Provides
    @Singleton
    fun provideTTSManager(@ApplicationContext context: Context): TTSManager = TTSManager(context)

    @Provides
    @Singleton
    fun provideDiscoveryTracker(@ApplicationContext context: Context): DiscoveryTracker = DiscoveryTracker(context)

    @Provides
    @Singleton
    fun provideQuizRepository(): QuizRepository = QuizRepository()

    /**
     * [SpatialContextProvider] → [OutdoorSpatialProvider] 바인딩.
     * Indoor 디바이스 지원 시 [IndoorSpatialProvider]로 교체하는 방식으로 확장 가능.
     * DevMode(GPS Mock) 지원 포함.
     */
    @Provides
    @Singleton
    fun provideSpatialContextProvider(
        locationProvider: LocationProvider,
        repository: HeritageRepository,
        devModeManager: DevModeManager
    ): SpatialContextProvider = OutdoorSpatialProvider(
        locationProvider = locationProvider,
        repository = repository,
        devModeManager = devModeManager
    )
}
