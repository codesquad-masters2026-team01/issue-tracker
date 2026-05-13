// src/pages/IssueListPage.tsx

import { useState } from "react";
import FilterBar from "../components/FilterBar.tsx";
import LabelMilestoneTabs from "../components/LabelMilestoneTabs.tsx";
import IssueListHeader from "../components/IssueListHeader.tsx";
import IssueItem, {type IssueType} from "../components/IssueItem.tsx";
import IssueSelectionHeader from "../components/IssueSelectionHeader.tsx";
// import { useIssues } from "../hooks/useIssues";

// TODO: 각자 데이터 --> 추후에 실제 서버에서 받아올 수 있도록 hooks 디렉토리 .ts 파일 사용
const mockIssues: IssueType[] = [
    { id: 1, status: 'open', title: 'FE 이슈트래커 개발', labels: [{ text: 'documentation', color: 'blue' }], authorName: 'samsamis9', timestamp: '8분 전', milestoneTitle: '그룹프로젝트:이슈트래커' },
    { id: 2, status: 'open', title: '디자인 시스템 컴포넌트 개발', labels: [{ text: 'feature', color: 'green' }], authorName: 'LeeJaeWan', timestamp: '22분 전', milestoneTitle: 'v1.0 릴리즈' },
    { id: 3, status: 'closed', title: 'README 파일 수정', labels: [], authorName: 'Wonjae-Song0906', timestamp: '1시간 전', milestoneTitle: null },
];

export default function IssueListPage() {
    // 선택된 이슈 ID들을 관리하는 상태
    const [selectedIds, setSelectedIds] = useState<number[]>([]);

    // 개별 아이템 선택 토글 함수
    const handleToggleItem = (id: number) => {
        setSelectedIds(prev =>
            prev.includes(id) ? prev.filter(selectedId => selectedId !== id) : [...prev, id]
        );
    };

    const isAllSelected = mockIssues.length > 0 && selectedIds.length === mockIssues.length;
    const hasSelection = selectedIds.length > 0;

    // 전체 선택/해제 토글 함수
    const handleToggleAll = () => {
        if(selectedIds.length === mockIssues.length) {
            setSelectedIds([]); // 모두 선택된 상태면 전체 해제
        } else {
            setSelectedIds(mockIssues.map(issue => issue.id)); // 아니면 전체 선택
        }
    }

    return (
        <main className="max-w-[1440px] mx-auto px-6 py-10">
            {/* 상단 필터/버튼 영역 */}
            <div className="flex justify-between items-center mb-6">
                <FilterBar />
                <div className="flex items-center gap-6">
                    <LabelMilestoneTabs />
                    <button className="flex items-center justify-center px-6 h-10 bg-[#007AFF] text-white rounded-xl text-sm font-bold">
                        + 이슈 작성
                    </button>
                </div>
            </div>

            {/* 이슈 목록 영역 */}
            <div className="bg-white border border-slate-200 rounded-xl shadow-sm overflow-hidden">
                {/* 4. 조건부 헤더 렌더링: 하나라도 선택되면 SelectionHeader, 아니면 기본 Header */}
                {hasSelection ? (
                    <IssueSelectionHeader
                        selectedCount={selectedIds.length}
                        isAllSelected={isAllSelected}
                        onToggleAll={handleToggleAll}
                        />
                ):(
                    <IssueListHeader
                        isAllSelected={isAllSelected}
                        onToggleAll={handleToggleAll}
                        />
                )}

                <div className="flex flex-col">
                    {mockIssues.map((issue) => (
                        <IssueItem 
                            key={issue.id} 
                            issue={issue} 
                            isSelected={selectedIds.includes(issue.id)} // 선택 여부 주입
                            onToggle={() => handleToggleItem(issue.id)} // 토글 함수 주입
                        /> 
                    ))}
                </div>
            </div>
        </main>
    );
}