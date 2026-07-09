export default function ErrorAlert({ message }) {
    if (!message) {
        return null;
    }

    return (
        <div className="mb-4 rounded-lg bg-red-100 p-3 text-sm text-red-600">
            {message}
        </div>
    );
}
