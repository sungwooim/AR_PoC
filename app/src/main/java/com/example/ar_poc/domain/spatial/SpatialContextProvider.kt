package com.example.ar_poc.domain.spatial

import kotlinx.coroutines.flow.Flow

/**
 * 공간 컨텍스트 제공자 인터페이스.
 *
 * 구현체를 교체하는 것만으로 GPS(OutdoorSpatialProvider) ↔ 실내 측위(IndoorSpatialProvider)
 * 전환이 가능하도록 추상화되어 있다.
 * Hilt를 통해 구체 클래스를 주입하며, UI/ViewModel은 이 인터페이스만 참조한다.
 *
 * 발행 주기: 구현체마다 다름
 *   - Outdoor: GPS 폴링 주기 (기본 5초)
 *   - Indoor : BLE/UWB 업데이트 콜백 기반 (실시간)
 */
interface SpatialContextProvider {

    /**
     * [SpatialContext]를 지속적으로 발행하는 Flow.
     * collect 시 즉시 최신 상태를 발행하고 이후 변경 때마다 갱신.
     */
    fun getSpatialContext(): Flow<SpatialContext>
}
