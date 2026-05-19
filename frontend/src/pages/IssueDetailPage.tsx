import { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import CommentItem from "../components/CommentItem.tsx";
import ListFilterDropdown from "../components/ListFilterDropdown.tsx";
import type { IssueDetail, IssueDetailResponse } from "../types/Issue";

// 데이터 타입 정의
interface Member { id: number; name: string; }
interface Label { id: number; name: string; backgroundColor: string; }
interface Milestone { id: number; name: string; }

function getRelativeTime(timestamp: string) {
    const now = new Date();
    const past = new Date(timestamp);
    const diffInMs = now.getTime() - past.getTime();

    const seconds = Math.floor(diffInMs / 1000);
    const minutes = Math.floor(seconds / 60);
    const hours = Math.floor(minutes / 60);
    const days = Math.floor(hours / 24);

    if (seconds < 60) return "방금 전";
    if (minutes < 60) return `${minutes}분 전`;
    if (hours < 24) return `${hours}시간 전`;
    if (days < 365) return `${days}일 전`;

    return timestamp.split(' ')[0];
}

export default function IssueDetailPage() {
    const { id } = useParams<{ id: string }>();
    const navigate = useNavigate();
    const [issue, setIssue] = useState<IssueDetail | null>(null);
    const [isLoading, setIsLoading] = useState(true);

    // 서버 데이터 상태 (사이드바 드롭다운용)
    const [allMembers, setAllMembers] = useState<Member[]>([]);
    const [allLabels, setAllLabels] = useState<Label[]>([]);
    const [allMilestones, setAllMilestones] = useState<Milestone[]>([]);

    useEffect(() => {
        const fetchIssueDetail = async () => {
            try {
                const response = await fetch(`http://localhost:8080/api/issues/${id}`);
                const result: IssueDetailResponse = await response.json();

                if (result.success) {
                    setIssue(result.data);
                } else {
                    alert(result.message);
                    navigate("/");
                }
            } catch (error) {
                console.error("이슈 상세 정보를 불러오는데 실패했습니다.", error);
                alert("데이터 로딩 중 오류가 발생했습니다.");
                navigate("/");
            } finally {
                setIsLoading(false);
            }
        };

        const fetchAllMetadata = async () => {
            try {
                const [mRes, lRes, miRes] = await Promise.all([
                    fetch("http://localhost:8080/api/members"),
                    fetch("http://localhost:8080/api/labels"),
                    fetch("http://localhost:8080/api/milestones")
                ]);

                const mResult = await mRes.json();
                const lResult = await lRes.json();
                const miResult = await miRes.json();

                if (mResult.success) setAllMembers(mResult.data);
                if (lResult.success) setAllLabels(lResult.data);
                if (miResult.success) setAllMilestones(miResult.data);
            } catch (error) {
                console.error("데이터 로딩 실패:", error);
            }
        };

        void fetchIssueDetail();
        void fetchAllMetadata();
    }, [id, navigate]);

    const handleToggleStatus = async () => {
        if (!issue) return;
        const newStatus = issue.isOpened ? "CLOSED" : "OPEN";
        const confirmMessage = issue.isOpened 
            ? "이 이슈를 닫으시겠습니까?" 
            : "이 이슈를 다시 여시겠습니까?";
        
        if (!window.confirm(confirmMessage)) return;

        try {
            const response = await fetch(`http://localhost:8080/api/issues/${id}`, {
                method: "PATCH",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({ status: newStatus })
            });
            const result = await response.json();

            if (result.success) {
                setIssue(prev => prev ? { ...prev, isOpened: !prev.isOpened } : null);
            } else {
                alert("상태 변경 실패: " + result.message);
            }
        } catch (error) {
            console.error("이슈 상태 변경 중 오류 발생:", error);
            alert("처리 중 오류가 발생했습니다.");
        }
    };

    const handleDelete = async () => {
        if (!window.confirm("정말로 이 이슈를 삭제하시겠습니까?")) return;

        try {
            const response = await fetch(`http://localhost:8080/api/issues/${id}`, {
                method: "DELETE"
            });
            const result = await response.json();

            if (result.success) {
                navigate("/");
            } else {
                alert("이슈 삭제 실패: " + result.message);
            }
        } catch (error) {
            console.error("이슈 삭제 중 오류 발생:", error);
            alert("삭제 처리 중 오류가 발생했습니다.");
        }
    };

    // 공통 체크 아이콘
    const CheckIcon = ({ isSelected }: { isSelected: boolean }) => (
        <svg className="w-4 h-4 text-[#4E4B66]" viewBox="0 0 16 16" fill="none">
            <circle cx="8" cy="8" r="7.2" stroke="currentColor" strokeWidth="1.6"/>
            {isSelected && (
                <path d="M5.33 8.33L7.33 10.33L10.67 6.67" stroke="currentColor" strokeWidth="1.6" strokeLinecap="round" strokeLinejoin="round"/>
            )}
        </svg>
    );

    if (isLoading) {
        return (
            <div className="flex justify-center items-center h-[calc(100vh-64px)]">
                <span className="text-slate-400">이슈 정보를 불러오는 중입니다...</span>
            </div>
        );
    }

    if (!issue) return null;

    return (
        <main className="max-w-[1440px] mx-auto px-6 py-10">
            {/* Header Section */}
            <header className="mb-8 pb-6 border-b border-slate-200">
                <div className="flex justify-between items-start mb-4">
                    <div className="flex items-center gap-4">
                        <h1 className="text-3xl font-bold text-slate-800">{issue.title}</h1>
                        <span className="text-3xl font-light text-slate-400">#{issue.id}</span>
                    </div>
                    <div className="flex items-center gap-2">
                        <button className="px-4 py-2 bg-white border border-slate-200 rounded-lg text-sm font-bold text-slate-700 hover:bg-slate-50">
                            편집
                        </button>
                        <button
                            onClick={handleToggleStatus}
                            className={`px-4 py-2 bg-white border rounded-lg text-sm font-bold transition-colors ${
                                issue.isOpened 
                                ? "border-[#FF3B30] text-[#FF3B30] hover:bg-red-50" 
                                : "border-[#007AFF] text-[#007AFF] hover:bg-blue-50"
                            }`}
                        >
                            {issue.isOpened ? "이슈 닫기" : "이슈 열기"}
                        </button>
                    </div>
                </div>

                <div className="flex items-center gap-4">
                    <div className={`flex items-center gap-1.5 px-3 py-1 rounded-full text-white text-sm font-bold ${issue.isOpened ? 'bg-[#007AFF]' : 'bg-[#FF3B30]'}`}>
                        <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <circle cx="12" cy="12" r="10" strokeWidth="2"></circle>
                            <path d="M12 8v4m0 4h.01" strokeWidth="2" strokeLinecap="round"></path>
                        </svg>
                        {issue.isOpened ? '열린 이슈' : '닫힌 이슈'}
                    </div>
                    <div className="text-slate-500 text-sm">
                        <span className="font-bold text-slate-700">{issue.author.name}</span>님이 {getRelativeTime(issue.createdAt)}에 이 이슈를 열었습니다 · 코멘트 {issue.commentCount}개
                    </div>
                </div>
            </header>

            {/* Content Section */}
            <div className="flex flex-col lg:flex-row gap-8">
                {/* Left: Comment Stream */}
                <div className="flex-grow relative">
                    {/* Vertical line connecting comments */}
                    <div className="absolute left-[22px] top-11 bottom-0 w-[1px] bg-slate-200 -z-10" />

                    {/* Issue Body (Initial Comment) */}
                    <CommentItem
                        isMainContent={true}
                        comment={{
                            id: 0,
                            author: issue.author,
                            contents: issue.contents,
                            createdAt: issue.createdAt,
                            isIssueAuthor: true
                        }}
                    />
                    
                    <div className="flex flex-col">
                        {issue.comments.map(comment => (
                            <CommentItem key={comment.id} comment={comment} />
                        ))}
                    </div>
                </div>

                {/* Right: Sidebar */}
                <div className="w-80 flex flex-col gap-2">
                    <aside className="flex flex-col h-fit bg-white border border-slate-200 rounded-xl shadow-sm">
                        {/* Assignees */}
                        <div className="p-6 border-b border-slate-200">
                            <div className="flex justify-between items-center mb-4">
                                <h3 className="text-sm font-bold text-slate-500">담당자</h3>
                                <ListFilterDropdown title={""}>
                                    <div className="flex flex-col w-full bg-[#D9DBE9] gap-[1px] rounded-[16px] overflow-hidden shadow-lg">
                                        <div className="px-4 py-2 bg-[#F7F7FC] text-[12px] font-medium text-[#4E4B66]">담당자 설정</div>
                                        {allMembers.map(member => (
                                            <button
                                                key={member.id}
                                                className="w-full h-[44px] px-4 py-2 bg-[#FEFEFE] hover:bg-[#F7F7FC] flex items-center justify-between transition-colors"
                                            >
                                                <span className={`text-[16px] text-[#14142B] ${issue.assignees.some(a => a.id === member.id) ? 'font-bold' : 'font-medium'}`}>{member.name}</span>
                                                <CheckIcon isSelected={issue.assignees.some(a => a.id === member.id)} />
                                            </button>
                                        ))}
                                    </div>
                                </ListFilterDropdown>
                            </div>
                            <div className="flex flex-col gap-2">
                                {issue.assignees.length === 0 ? (
                                    <span className="text-sm text-slate-400">담당자 없음</span>
                                ) : (
                                    issue.assignees.map(assignee => (
                                        <div key={assignee.id} className="flex items-center gap-2">
                                            <img
                                                src={`https://avatars.githubusercontent.com/${assignee.name}?s=20&v=4`}
                                                alt={assignee.name}
                                                className="w-5 h-5 rounded-full border border-slate-200"
                                            />
                                            <span className="text-sm font-medium text-slate-700">{assignee.name}</span>
                                        </div>
                                    ))
                                )}
                            </div>
                        </div>

                        {/* Labels */}
                        <div className="p-6 border-b border-slate-200">
                            <div className="flex justify-between items-center mb-4">
                                <h3 className="text-sm font-bold text-slate-500">레이블</h3>
                                <ListFilterDropdown title={""}>
                                    <div className="flex flex-col w-full bg-[#D9DBE9] gap-[1px] rounded-[16px] overflow-hidden shadow-lg">
                                        <div className="px-4 py-2 bg-[#F7F7FC] text-[12px] font-medium text-[#4E4B66]">레이블 설정</div>
                                        {allLabels.map(label => (
                                            <button
                                                key={label.id}
                                                className="w-full h-[44px] px-4 py-2 bg-[#FEFEFE] hover:bg-[#F7F7FC] flex items-center justify-between transition-colors"
                                            >
                                                <div className="flex items-center gap-2">
                                                    <div className="w-3 h-3 rounded-full" style={{backgroundColor: label.backgroundColor}} />
                                                    <span className={`text-[16px] text-[#14142B] ${issue.labels.some(l => l.id === label.id) ? 'font-bold' : 'font-medium'}`}>{label.name}</span>
                                                </div>
                                                <CheckIcon isSelected={issue.labels.some(l => l.id === label.id)} />
                                            </button>
                                        ))}
                                    </div>
                                </ListFilterDropdown>
                            </div>
                            <div className="flex flex-wrap gap-2">
                                {issue.labels.length === 0 ? (
                                    <span className="text-sm text-slate-400">레이블 없음</span>
                                ) : (
                                    issue.labels.map(label => (
                                        <span
                                            key={label.id}
                                            className="px-3 py-1 rounded-full text-xs font-bold"
                                            style={{ backgroundColor: label.backgroundColor, color: label.textColor }}
                                        >
                                            {label.name}
                                        </span>
                                    ))
                                )}
                            </div>
                        </div>

                        {/* Milestone */}
                        <div className="p-6">
                            <div className="flex justify-between items-center mb-4">
                                <h3 className="text-sm font-bold text-slate-500">마일스톤</h3>
                                <ListFilterDropdown title={""}>
                                    <div className="flex flex-col w-full bg-[#D9DBE9] gap-[1px] rounded-[16px] overflow-hidden shadow-lg">
                                        <div className="px-4 py-2 bg-[#F7F7FC] text-[12px] font-medium text-[#4E4B66]">마일스톤 설정</div>
                                        {allMilestones.map(ms => (
                                            <button
                                                key={ms.id}
                                                className="w-full h-[44px] px-4 py-2 bg-[#FEFEFE] hover:bg-[#F7F7FC] flex items-center justify-between transition-colors"
                                            >
                                                <span className={`text-[16px] text-[#14142B] ${issue.milestone?.id === ms.id ? 'font-bold' : 'font-medium'}`}>{ms.name}</span>
                                                <CheckIcon isSelected={issue.milestone?.id === ms.id} />
                                            </button>
                                        ))}
                                    </div>
                                </ListFilterDropdown>
                            </div>
                            <div className="flex flex-col gap-2">
                                {issue.milestone ? (
                                    <>
                                        <div className="flex items-center gap-2 text-[#4E4B66]">
                                            <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M9 20v-6a2 2 0 012-2h4.5m0 0l-3-3m3 3l-3 3M9 8V4m0 4a2 2 0 00-2-2H5a2 2 0 00-2 2v4a2 2 0 002 2h2a2 2 0 002-2V8z"></path>
                                            </svg>
                                            <span className="text-sm font-medium">{issue.milestone.name}</span>
                                        </div>
                                        <div className="w-full bg-slate-100 rounded-full h-2 mt-1">
                                            <div
                                                className="bg-[#007AFF] h-2 rounded-full"
                                                style={{ width: `${issue.milestone.progress}%` }}
                                            />
                                        </div>
                                    </>
                                ) : (
                                    <span className="text-sm text-slate-400">마일스톤 없음</span>
                                )}
                            </div>
                        </div>
                    </aside>

                    {/* Separate Delete Action Button below the sidebar box */}
                    <div className="flex justify-end pr-2">
                        <button 
                            onClick={handleDelete}
                            className="flex items-center gap-1.5 px-3 py-1.5 text-xs font-bold text-[#FF3B30] hover:bg-red-50 rounded-lg transition-colors"
                        >
                            <svg className="w-3.5 h-3.5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16" />
                            </svg>
                            이슈 삭제
                        </button>
                    </div>
                </div>
            </div>
        </main>
    );
}