package com.example.ar_poc

object Config {
    /**
     * 컴파일 타임 기본값. 런타임 토글은 ARViewModel._isDevMode(StateFlow)가 담당.
     * true: 실내 테스트 모드 (Mock GPS 사용)
     * false: 실제 현장 모드
     */
    const val DEV_MODE = false

    // Mock GPS coordinates — 경복궁 정중앙 (DevMode AR/지도 테스트용)
    // 근정전과 향원정 사이, 모든 POI가 사방에서 보이는 최적 위치
    const val MOCK_LATITUDE = 37.5796
    const val MOCK_LONGITUDE = 126.9770
}
