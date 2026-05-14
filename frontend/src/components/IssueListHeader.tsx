// src/components/IssueListHeader.tsx
import ListFilterDropdown from "./ListFilterDropdown.tsx";
import AssigneeFilterContent from "./filter/AssigneeFilterContent.tsx";

// 1. 프롭 타입에 openCount와 closedCount를 추가 정의
interface IssueListHeaderProps {
    isAllSelected: boolean;
    onToggleAll: () => void;
    openCount: number;
    closedCount: number;
}

export default function IssueListHeader({
                                            isAllSelected,
                                            onToggleAll,
                                            openCount,
                                            closedCount
                                        }: IssueListHeaderProps) {

    return (
        <div className="flex items-center w-full h-[64px] bg-[#F7F7FC] rounded-t-[16px] px-8 border-b border-slate-200 gap-6">
            <input
                type="checkbox"
                id="headerSelecAllCheckbox"
                checked={isAllSelected}
                onChange={onToggleAll}
                className="w-4 h-4 rounded-[2px] border-[1.6px] border-[#D9DBE9] text-blue-600 focus:ring-0 cursor-pointer"
            />

            <div className="flex items-center gap-6 ml-8">
                {/* [A] 열린 이슈 */}
                <button className="flex items-center gap-1 cursor-pointer">
                    <svg className="w-4 h-4 text-[#14142B]" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <circle cx="12" cy="12" r="10" strokeWidth="2"></circle>
                        <path d="M12 8v4m0 4h.01" strokeWidth="2" strokeLinecap="round"></path>
                    </svg>
                    <span className="font-['Pretendard'] text-[16px] font-bold text-[#14142B]">
                    {/* 2. 하드코딩된 숫자 대신 전달받은 데이터 출력 */}
                        열린 이슈({openCount})
                </span>
                </button>

                {/* [B] 닫힌 이슈 */}
                <button className="flex items-center gap-1 cursor-pointer hover:opacity-80 transition-opacity">
                    <svg className="w-4 h-4 text-[#4E4B66]" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M5 8h14M5 8a2 2 0 110-4h14a2 2 0 110 4M5 8v10a2 2 0 002 2h10a2 2 0 002-2V8m-9 4h4"></path>
                    </svg>
                    <span className="font-['Pretendard'] text-[16px] font-medium text-[#4E4B66]">
                    {/* 3. 하드코딩된 숫자 대신 전달받은 데이터 출력 */}
                        닫힌 이슈({closedCount})
                </span>
                </button>
            </div>

            <div className="flex items-center gap-8 ml-auto">
                <ListFilterDropdown title="담당자">
                    <AssigneeFilterContent />
                </ListFilterDropdown>

                <ListFilterDropdown title="레이블">
                    {/*레이블 팝업 내용*/}
                </ListFilterDropdown>

                <ListFilterDropdown title="마일스톤">
                    {/*마일스톤 팝업 내용*/}
                </ListFilterDropdown>

                <ListFilterDropdown title="작성자">
                    {/*작성자 팝업 내용*/}
                </ListFilterDropdown>
            </div>
        </div>
    );
}