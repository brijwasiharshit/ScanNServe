import Button from "../common/Button";
import InputField from "../common/InputField";

export default function AdminForm() {
    return (
        <form>
            <InputField placeholder="Admin Name" />
            <InputField placeholder="Admin Email" className="mt-3" />
            <InputField placeholder="Password" type="password" className="mt-3" />

            <Button variant="success" type="submit" className="mt-4 w-full">
                Create Admin
            </Button>
        </form>
    );
}
