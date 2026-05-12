// src/components/IssueListHeader.tsx

export default function IssueListHeader() {
    const filterOptions = ['담당자', '레이블', '마일스톤', '작성자'];

  return (
    // 1. 전체 컨테이너 (List Header)
    // w-full: 부모 컨테이너에 맞춰 꽉 채움
    // h-[64px]: 기획서 명세 반영
    // rounded-t-[16px]: 상단 양쪽 모서리만 16px만큼 둥글게 처리 (border-top-left/right-radius)
    // px-8: 체크박스가 화면 왼쪽 끝에 딱 붙지 않도록 내부 여백(Padding)을 임의로 주었습니다.
    <div className="flex items-center w-full h-[64px] bg-[#F7F7FC] rounded-t-[16px] px-8 border-b border-slate-200 gap-6">

      {/* 2. Ractangle 17 (체크박스) */}
      <input
        type="checkbox"
        className="w-4 h-4 rounded-[2px] border-[1.6px] border-[#D9DBE9] text-blue-600 focus:ring-0 cursor-pointer"
      />

        {/* 2. 탭 영역 (체크박스와 32px(ml-8) 정도 떨어져 있다고 가정하여 여백 추가) */}
        <div className="flex items-center gap-6 ml-8">

            {/* [A] 열린 이슈 (선택된 상태 - Active) */}
            {/* gap-1은 기획서의 gap: 4px를 구현합니다. */}
            <button className="flex items-center gap-1 cursor-pointer">
                {/* alertCircle 아이콘 */}
                <svg className="w-4 h-4 text-[#14142B]" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <circle cx="12" cy="12" r="10" strokeWidth="2"></circle>
                    <path d="M12 8v4m0 4h.01" strokeWidth="2" strokeLinecap="round"></path>
                </svg>
                <span className="font-['Pretendard'] text-[16px] font-bold text-[#14142B]">
            열린 이슈(2)
          </span>
            </button>

            {/* [B] 닫힌 이슈 (기본 상태 - Enabled) */}
            <button className="flex items-center gap-1 cursor-pointer hover:opacity-80 transition-opacity">
                {/* archive 아이콘 */}
                <svg className="w-4 h-4 text-[#4E4B66]" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M5 8h14M5 8a2 2 0 110-4h14a2 2 0 110 4M5 8v10a2 2 0 002 2h10a2 2 0 002-2V8m-9 4h4"></path>
                </svg>
                <span className="font-['Pretendard'] text-[16px] font-medium text-[#4E4B66]">
            닫힌 이슈(0)
          </span>
            </button>
        </div>

        {/* 🔵 [우측 영역]: 드롭다운 필터들 */}
        <div className="flex items-center gap-8 ml-auto">

            {/* 배열을 순회하며 똑같이 생긴 버튼 4개를 찍어냅니다. */}
            {filterOptions.map((optionName) => (
                // flex, gap-1(4px) 적용. 마우스 호버 시 텍스트가 짙은 색으로 변하는 인터랙션 추가
                <button
                    key={optionName}
                    className="flex items-center gap-1 text-[#4E4B66] hover:text-[#14142B] transition-colors cursor-pointer"
                >
            <span className="font-['Pretendard'] text-[16px] font-medium">
              {optionName}
            </span>
                    {/* 아래 화살표 (chevronDown) 아이콘 */}
                    <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M19 9l-7 7-7-7"></path>
                    </svg>
                </button>
            ))}

        </div>
    </div>
  );
}