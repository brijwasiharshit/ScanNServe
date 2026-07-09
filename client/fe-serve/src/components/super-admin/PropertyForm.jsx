import Button from "../common/Button";
import InputField from "../common/InputField";

export default function PropertyForm({ onNext }) {
    return (
        <form
            onSubmit={(event) => {
                event.preventDefault();
                onNext();
            }}
        >
            <InputField placeholder="Property Name" />
            <InputField placeholder="Address" className="mt-3" />
            <InputField placeholder="Contact Number" className="mt-3" />

            <Button type="submit" className="mt-4 w-full">
                Next
            </Button>
        </form>
    );
}
