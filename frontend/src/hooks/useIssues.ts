// src/hooks/useIssues.ts

import { useState, useEffect } from "react";
import { type IssueType } from "../components/IssueItem.tsx";

export function useIssues() {
    const [issues, setIssues] = useState<IssueType[]>([]);
    const [isLoading, setIsLoading] = useState<boolean>(true);
    const [error, setError] = useState<Error | null>(null);

    const fetchIssues = async () => {
        try {
            setIsLoading(true);

            const response = await fetch('/api/issues?state=open');

            if (!response.ok) {
                throw new Error("Failed to fetch issues");
            }

            const data = await response.json();
            setIssues(data);
        } catch (err) {
            setError(err instanceof Error ? err.message : '알 수 없는 에러 발생');
        } finally {
            setIsLoading(false);
        }
    };

    useEffect(() => {
        fetchIssues();
    }, []);

    return { issues, isLoading, error, refetch: fetchIssues};
}