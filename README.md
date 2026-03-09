# 🍽️ DineQ - 테이블 QR 오더 서비스

고객과 점주를 연결하는 매장 주문 관리 서비스
<div align="center">
  <img src="https://github.com/user-attachments/assets/183a166a-a2c1-4832-b14e-e28dc2eb8616" width="60%" />
</div>

## 📖 프로젝트 소개

DineQ는 매장 QR 주문 서비스로, 고객의 주문 편의성을 높이고 점주의 운영 효율을 개선하기 위해 개발되었습니다.

- 매장 고객이 사용하는 웹, 점주가 사용하는 앱 환경을 충족하면서 빠르게 개발 가능한 WebView 형태로 개발
- 현재 누적 주문 2,200건 이상으로 6개월가량 **안정적으로 서비스** 중
- 프로젝트 기간: 2025년 5월 15일 → 2025년 6월 16일

## ✨ 주요 기능

### 고객용 웹 서비스

- QR 코드 스캔을 통한 간편한 주문
- 메뉴 카테고리별 조회 및 장바구니 관리
- 실시간 주문 상태 확인

### 점주용 앱 서비스

- 실시간 주문 목록 조회 및 관리
- 테이블별 정산 기능
- 메뉴 관리 시스템

## 🖥️ 서비스 UI

### 👤 고객 화면
<div align="center">
  <img src="https://github.com/user-attachments/assets/e30a26ed-ce0a-4f2c-b0b0-d2e7b18b84a5" width="30%"  />
  <img src="https://github.com/user-attachments/assets/e66a213f-a9ac-46b6-a0ec-f769aa08b3e6" width="30%" style="margin: 0 10px;" />
    <img src="https://github.com/user-attachments/assets/42e555d5-c405-473d-bf64-d40b68f9d184" width="30%" />
</div>

### 🏪 점주 화면
<div align="center">
  <img src="https://github.com/user-attachments/assets/05fab649-bc1c-4c45-b557-85c46681c417" width="30%" />
  <img src="https://github.com/user-attachments/assets/2c4a34f8-8803-4225-b927-df37da2d9166" width="30%" style="margin: 0 10px;" />
  <img src="https://github.com/user-attachments/assets/ad07e051-c8e4-4a14-bb26-8bb2a0012b79" width="30%" />
</div>


## 🛠️ 기술 스택
### 🏗️ Backend Architecture
- Framework: Spring Boot
- Database: MariaDB
- Cache / Session Store: Redis (세션 관리 및 데이터 캐싱)
- Deployment: Amazon EC2
- Containerization: Docker Compose를 활용하여 DB 및 Redis 관리

### 🎨 Frontend Architecture
고객용 웹
- Framework: Next.js
- Styling: TailwindCSS
- State Management / Data Fetching: TanStack Query

점주용 앱
- Framework: React Native

Redirect URI를 통한 앱 복귀 및 세션 유지
## 🏗️ 서비스 아키텍쳐
<img width="1024" height="741" alt="image" src="https://github.com/user-attachments/assets/a0405a47-3cf5-40ff-be51-f7bab3a80ade" />

## 📊 서비스 성과
- 누적 주문: **2,200건 이상**
- 안정적 서비스 운영: **6개월+**
- 실제 매장에서 사용 중인 프로덕션 서비스

## 👥 팀 구성
- 프론트엔드: 1명
- 백엔드: 2명
