import { X } from "lucide-react";

export default function Modal({ title, children, onClose }) {
    return (
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/40 p-4">
            <div className="w-full max-w-lg rounded-2xl bg-white p-6 shadow-xl">
                <div className="mb-5 flex items-center justify-between">
                    <h2 className="text-xl font-bold text-slate-900">{title}</h2>

                    <button
                        type="button"
                        onClick={onClose}
                        className="rounded-lg p-1 text-slate-500 hover:bg-slate-100"
                        aria-label="Close modal"
                    >
                        <X />
                    </button>
                </div>

                {children}
            </div>
        </div>
    );
}
