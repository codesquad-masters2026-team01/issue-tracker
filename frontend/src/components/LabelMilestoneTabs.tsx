// src/components/LabelMilestoneTabs.tsx

// 1. 프롭 타입 정의
interface LabelMilestoneTabsProps {
    labelCount: number;
    milestoneCount: number;
}

export default function LabelMilestoneTabs({ labelCount, milestoneCount }: LabelMilestoneTabsProps) {
    return (
        <div className="flex w-[320px] h-10 bg-[#D9DBE9] border border-[#D9DBE9] rounded-xl overflow-hidden gap-[1px]">

            {/* 2. 레이블 버튼 */}
            <button className="flex-1 flex items-center justify-center gap-1 bg-[#F7F7FC] hover:bg-white transition-colors cursor-pointer">
                <svg className="w-4 h-4 text-[#4E4B66]" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M7 7h.01M7 3h5c.512 0 1.024.195 1.414.586l7 7a2 2 0 010 2.828l-7 7a2 2 0 01-2.828 0l-7-7A1.994 1.994 0 013 12V7a4 4 0 014-4z"></path>
                </svg>
                <span className="font-['Pretendard'] text-[16px] font-medium text-[#4E4B66]">
                    레이블({labelCount})
                </span>
            </button>

            {/* 3. 마일스톤 버튼 */}
            <button className="flex-1 flex items-center justify-center gap-1 bg-[#F7F7FC] hover:bg-white transition-colors cursor-pointer">
                <svg className="w-4 h-4 text-[#4E4B66]" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M9 20v-6a2 2 0 012-2h4.5m0 0l-3-3m3 3l-3 3M9 8V4m0 4a2 2 0 00-2-2H5a2 2 0 00-2 2v4a2 2 0 002 2h2a2 2 0 002-2V8z"></path>
                </svg>
                <span className="font-['Pretendard'] text-[16px] font-medium text-[#4E4B66]">
                    마일스톤({milestoneCount})
                </span>
            </button>

        </div>
    );
}