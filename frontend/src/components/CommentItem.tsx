import type { Comment as CommentType } from "../types/Issue";

interface CommentItemProps {
    comment: CommentType;
    isMainContent?: boolean;
}

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

export default function CommentItem({ comment, isMainContent = false }: CommentItemProps) {
    const { author, contents, createdAt, isIssueAuthor } = comment;

    return (
        <div className={`flex gap-4 mb-8 ${isMainContent ? 'relative' : ''}`}>
            {/* User Avatar */}
            <img
                src={`https://avatars.githubusercontent.com/${author.name}?s=44&v=4`}
                alt={`${author.name}'s profile`}
                className="w-11 h-11 rounded-full border border-slate-200 object-cover"
                onError={(e) => {
                    (e.target as HTMLImageElement).src = 'https://www.gravatar.com/avatar/00000000000000000000000000000000?d=mp&f=y';
                }}
            />

            {/* Comment Box */}
            <div className={`flex-grow border rounded-xl overflow-hidden shadow-sm bg-white ${isMainContent ? 'border-[#007AFF]' : 'border-slate-200'}`}>
                {/* Header */}
                <div className={`flex justify-between items-center px-6 py-3 border-b ${isMainContent ? 'bg-[#EFF7FF] border-[#007AFF]/20' : 'bg-slate-50 border-slate-200'}`}>
                    <div className="flex items-center gap-2">
                        <span className="font-bold text-slate-700">{author.name}</span>
                        <span className="text-slate-400 text-sm">{getRelativeTime(createdAt)}</span>
                    </div>
                    <div className="flex items-center gap-2">
                        {isIssueAuthor && (
                            <div className="px-2 py-0.5 border border-slate-200 rounded-full text-[10px] font-bold text-slate-500 uppercase">
                                Author
                            </div>
                        )}
                        {isMainContent && (
                            <div className="px-2 py-0.5 bg-[#007AFF] rounded-full text-[10px] font-bold text-white uppercase">
                                Initial Post
                            </div>
                        )}
                    </div>
                </div>

                {/* Content */}
                <div className={`px-6 py-6 text-[#4E4B66] leading-relaxed whitespace-pre-wrap ${isMainContent ? 'text-lg' : 'text-base'}`}>
                    {contents}
                </div>
            </div>
            
            {/* Vertical Line for stream effect - only if not main content or if you want to connect them */}
            {!isMainContent && (
                <div className="absolute left-[22px] -top-8 w-[1px] h-8 bg-slate-200 -z-10" />
            )}
        </div>
    );
}