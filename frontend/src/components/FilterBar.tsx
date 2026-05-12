// src/FilterBar.tsx

export default function FilterBar() {
    return (
        // 1. 전체 컨테이너 (filterBar)
        // w-[560px], h-[40px](h-10), 테두리 곡률(rounded-xl), 배경색 지정
        // gap-[1px]을 주어 버튼과 인풋 사이의 구분선을 만듭니다.
        <div className="flex items-center w-[560px] h-10 bg-[#D9DBE9] border border-[#D9DBE9] rounded-xl overflow-hidden gap-[1px]">

            {/* 2. 왼쪽: 필터 버튼 */}
            {/* w-[128px], h-full(40px 꽉 채움), 배경색 지정 */}
            {/* hover:bg-white 등을 주어 마우스가 올라갔을 때 상호작용을 추가했습니다. */}
            <button className="flex items-center justify-between w-[128px] h-full px-6 bg-[#F7F7FC] hover:bg-white transition-colors cursor-pointer">
                <span className="text-sm font-medium text-slate-700">필터</span>
                {/* 아래 화살표 아이콘 (드롭다운을 암시) */}
                <svg className="w-4 h-4 text-slate-500" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M19 9l-7 7-7-7"></path>
                </svg>
            </button>

            {/* 3. 오른쪽: 텍스트 입력창 (TextInput) */}
            {/* w-[431px], 배경색 지정 (width를 명시해도 되지만, flex-grow(flex-1)를 쓰면 남은 공간을 알아서 꽉 채웁니다) */}
            <div className="flex items-center flex-1 h-full px-6 bg-[#EFF0F6] gap-2">
                {/* 돋보기 아이콘 (Icons/search) */}
                <svg className="w-4 h-4 text-slate-400" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"></path>
                </svg>

                {/* 실제 입력 영역 (typingStates/placeholder) */}
                {/* focus:outline-none을 주어야 클릭 시 파란색 못생긴 테두리가 생기지 않습니다. */}
                <input
                    type="text"
                    placeholder="is:issue is:open"
                    className="w-full h-6 bg-transparent text-sm text-slate-900 placeholder-slate-500 focus:outline-none"
                />
            </div>

        </div>
    );
}