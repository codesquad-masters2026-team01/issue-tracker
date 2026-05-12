// src/IssueItem.tsx

// 나중에 App.tsx에서 쓸 데이터 타입(구조)도 여기서 한 번에 정의해 주면 좋습니다.
export interface IssueType {
    id: number;
    status: string;
    title: string;
    labels: { text: string; color: string }[];
    authorName: string;
    timestamp: string;
    milestoneTitle: string | null;
}

// 오직 '이슈 한 줄'을 그리는 역할만 담당하는 독립적인 모듈입니다.
export default function IssueItem({ issue }: { issue: IssueType }) {
    const { id, status, title, labels, authorName, timestamp, milestoneTitle } = issue;
    const statusIconColor = status === 'open' ? 'text-green-500' : 'text-purple-500';

    return (
        // 1. [전체 div] (openIssueInitialCell)
        // width: 1280 하드코딩 대신 w-full 사용. height: 96px 고정. bg-[#FEFEFE] 적용.
        // top: 16px 수치를 맞추기 위해 py-4(위아래 16px 패딩)를 주고 상단 정렬(items-start)을 합니다.
        <div className="flex items-start w-full h-[96px] bg-[#FEFEFE] border-b border-slate-200 px-8 py-4 hover:bg-slate-50 transition-colors">

            {/* 2. [체크박스] */}
            {/* 제목(높이 32px)의 중앙에 오도록 살짝 마진(mt-1.5)을 줍니다. */}
            <div className="mt-1.5">
                <input
                    type="checkbox"
                    id={`issueCheckbox${issue.id}`} // 아이디는 카멜 표기법 적용
                    className="w-4 h-4 rounded-[2px] border-[1.6px] border-[#D9DBE9] text-blue-600 focus:ring-0 cursor-pointer"
                />
            </div>

            {/* 3. [아이콘 - 이슈 제목 - 라벨 div] 묶음 */}
            {/* left: 80px를 구현하기 위해 체크박스와 콘텐츠 사이에 ml-8(32px) 여백을 줍니다.
          (px-8 + 체크박스 너비 + ml-8이 합쳐져 대략적인 left 위치를 잡음) */}
            <div className="flex flex-col ml-8 flex-1">

                {/* 상단 1번째 줄 (높이 32px, 요소 간격 8px) */}
                <div className="flex items-center gap-2 h-[32px]">

                    {status === 'open' ? (
                        <svg className="w-4 h-4 text-blue-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <circle cx="12" cy="12" r="10" strokeWidth="2"></circle>
                            <path d="M12 8v4m0 4h.01" strokeWidth="2" strokeLinecap="round"></path>
                        </svg>
                    ) : (
                        <svg className="w-4 h-4 text-red-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M5 8h14M5 8a2 2 0 110-4h14a2 2 0 110 4M5 8v10a2 2 0 002 2h10a2 2 0 002-2V8m-9 4h4"></path>
                        </svg>
                    )}

                    {/* [이슈 제목] */}
                    <h2 className="font-['Pretendard'] text-[20px] font-medium text-[#14142B] leading-[32px]">
                        {title}
                    </h2>

                    {/* [InformationTag] 렌더링 */}
                    {/* 배열로 받은 labels를 순회하며 태그를 생성합니다. */}
                    {labels.map((label) => (
                        <div
                            key={label.text}
                            className="flex items-center justify-center h-6 px-2 rounded-[12px] border border-[#D9DBE9] bg-[#FEFEFE]"
                        >
                            {/* [라벨 Text] */}
                            <span className="font-['Pretendard'] text-[12px] font-medium text-[#6E7191] leading-[16px] text-center">
                {label.text}
              </span>
                        </div>
                    ))}

                </div>

                {/* 🎯 하단: [이슈번호, 작성자/시간, 마일스톤] (height 24px) */}
                {/* gap-4는 기획서의 gap: 16px를 구현합니다. */}
                <div className="flex items-center gap-4 h-6 mt-1 text-[#6E7191] font-['Pretendard'] text-[16px] font-medium leading-[24px]">

                    {/* [이슈번호] */}
                    <span>#{id}</span>

                    {/* [작성자 및 타임스탬프 정보] */}
                    <span>이 이슈는 {timestamp}에 {authorName}님에 의해 작성되었습니다.</span>

                    {/* [마일스톤]: 데이터가 있을 때만 렌더링 */}
                    {milestoneTitle && (
                        <div className="flex items-center gap-2">
                            {/* 마일스톤 이미지 (Icons/milestone) */}
                            <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M9 20v-6a2 2 0 012-2h4.5m0 0l-3-3m3 3l-3 3M9 8V4m0 4a2 2 0 00-2-2H5a2 2 0 00-2 2v4a2 2 0 002 2h2a2 2 0 002-2V8z"></path>
                            </svg>
                            <span>{milestoneTitle}</span>
                        </div>
                    )}
                </div>
            </div>

            {/* 🎯 4. [NEW] 작성자 프로필 사진 (맨 우측 배치) */}
            {/* w-5 h-5 (20x20px), rounded-full(둥글게)
                 left: 1206px -> 부모의 flex 속성에 의해 자연스럽게 맨 우측에 배치됩니다.
                 top: 38px -> 부모의 py-4(16px) 여백을 제외한 mt-[22px] 마진을 부여하여 정밀한 위치를 잡습니다.
                 (이미지가 아직 데이터에 없으므로 작성자 이름을 기반으로 한 GitHub 아바타 API를 더미로 사용합니다)
              */}
            <img
                src={`https://avatars.githubusercontent.com/${authorName}?s=40&v=4`}
                alt={`${authorName}'s profile`}
                className="w-5 h-5 rounded-full mt-[22px] border border-slate-200 object-cover ml-4"
                onError={(e) => {
                    // 이미지 로드 실패 시 기본 아바타로 대체하는 백엔드적 안전장치
                    (e.target as HTMLImageElement).src = 'https://www.gravatar.com/avatar/00000000000000000000000000000000?d=mp&f=y';
                }}
            />
        </div>
    );
}

export interface IssueItemProps {}