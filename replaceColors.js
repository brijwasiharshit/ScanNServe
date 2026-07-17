const fs = require('fs');
const path = require('path');

const directoryPath = path.join(__dirname, 'client/fe-serve/src');
const search = '#0891B2';
const replacement = '#F97316'; // Orange-500

function walkDir(dir) {
    const files = fs.readdirSync(dir);
    for (const file of files) {
        const fullPath = path.join(dir, file);
        if (fs.statSync(fullPath).isDirectory()) {
            walkDir(fullPath);
        } else if (fullPath.endsWith('.jsx') || fullPath.endsWith('.js') || fullPath.endsWith('.css')) {
            let content = fs.readFileSync(fullPath, 'utf8');
            if (content.includes(search)) {
                content = content.replace(new RegExp(search, 'g'), replacement);
                // Also replace hover states that were tied to #155E75 (cyan-800) with #C2410C (orange-700)
                content = content.replace(new RegExp('#155E75', 'g'), '#C2410C');
                fs.writeFileSync(fullPath, content, 'utf8');
                console.log('Updated', fullPath);
            }
        }
    }
}

walkDir(directoryPath);
console.log('Done replacing colors.');
