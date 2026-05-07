import './index.css'; // 이 줄이 없으면 테일윈드가 작동하지 않아요!

function App() {
    return (
        // bg-slate-100 (회색 배경)과 text-blue-500 (파란 글씨)가 먹혀야 합니다!
        <div className="flex justify-center items-center h-screen bg-slate-100">
            <h1 className="text-5xl font-extrabold text-blue-600">
                드디어 테일윈드 성공! 🎉
            </h1>
        </div>
    );
}

export default App;