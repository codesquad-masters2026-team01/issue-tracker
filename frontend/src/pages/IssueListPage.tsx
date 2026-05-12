// src/pages/IssueListPage.tsx

import FilterBar from "../components/FilterBar.tsx";
import LabelMilestoneTabs from "../components/LabelMilestoneTabs.tsx";
import IssueListHeader from "../components/IssueListHeader.tsx";
import IssueItem, {type IssueType} from "../components/IssueItem.tsx";
// import { useIssues } from "../hooks/useIssues";

const mockIssues: IssueType[] = [
    { id: 1, status: 'open', title: 'FE 이슈트래커 개발', labels: [{ text: 'documentation', color: 'blue' }], authorName: 'samsamis9', timestamp: '8분 전', milestoneTitle: '그룹프로젝트:이슈트래커' },
    { id: 2, status: 'open', title: '디자인 시스템 컴포넌트 개발', labels: [{ text: 'feature', color: 'green' }], authorName: 'LeeJaeWan', timestamp: '22분 전', milestoneTitle: 'v1.0 릴리즈' },
    { id: 3, status: 'closed', title: 'README 파일 수정', labels: [], authorName: 'Wonjae-Song0906', timestamp: '1시간 전', milestoneTitle: null },
];

export default function IssueListPage() {
    // const { issues, isLoading, error } = useIssues();
    //
    // if (isLoading) return <div className={"p-10 text-center"}로딩 중...></div>
    // if (error) return <div className={"p-10 text-red-500 text-center"}>{error}</div>;

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
                <IssueListHeader />
                <div className="flex flex-col">
                    {mockIssues.map((issue) => (
                        <IssueItem key={issue.id} issue={issue} />
                    ))}
                </div>
            </div>
        </main>
    );
}