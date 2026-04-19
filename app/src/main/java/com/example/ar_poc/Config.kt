package com.example.ar_poc

object Config {
    /**
     * 컴파일 타임 기본값. 런타임 토글은 ARViewModel._isDevMode(StateFlow)가 담당.
     * true: 실내 테스트 모드 (Mock GPS 사용)
     * false: 실제 현장 모드
     */
    const val DEV_MODE = false

    // Mock GPS coordinates — 근정전 위치 (DevMode 테스트용 출발점)
    // 이전 37.5796은 구글맵 지도 상에서 강녕전 근처로 표시되어 40/60/90분 코스의
    // 1번 waypoint(근정전)와 정렬이 맞지 않았음. 이를 남쪽으로 조정.
    const val MOCK_LATITUDE = 37.5784
    const val MOCK_LONGITUDE = 126.9770
}
