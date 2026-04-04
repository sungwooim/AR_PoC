package com.example.ar_poc.data.location

import com.example.ar_poc.domain.spatial.SpatialContext
import com.example.ar_poc.domain.spatial.SpatialContextProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

/**
 * 실내 측위(BLE/UWB) 기반 SpatialContextProvider — 현재 Stub 구현.
 *
 * 실제 구현 시 BLE Beacon 또는 UWB Ranging API를 이용해
 * 실내 좌표(x, y, floor)를 GPS 좌표로 변환하여 발행한다.
 *
 * 현재는 [SpatialContext.EMPTY]를 하나 발행하고 종료.
 * AppModule에서 @Named("indoor") 한정자로 구분하여 주입 가능.
 */
class IndoorSpatialProvider @Inject constructor() : SpatialContextProvider {

    override fun getSpatialContext(): Flow<SpatialContext> {
        // TODO: BLE/UWB Ranging → indoor (x, y) → GPS 변환 → SpatialContext 발행
        return flowOf(SpatialContext.EMPTY.copy(isIndoor = true))
    }
}
