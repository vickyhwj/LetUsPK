function stepCheck(a, oldX, oldY, stepX, stepY) {
    if (stepX < 0 || stepX > 9 || stepY < 0 || stepY > 8) return false;
    //红方
    if (a[oldX][oldY].indexOf("r_") >= 0) {
        if (a[oldX][oldY].indexOf("bing") >= 0) {
            if (stepX > oldX) return false;
            if (Math.abs(stepY - oldY) + Math.abs(stepX - oldX) != 1) return false;
            if (stepY != oldY) {
                if (Math.abs(stepY - oldY) != 1) return false;
                if (!(oldX >= 0 && oldX <= 4)) return false;
                return true;
            }
            return true;
        }
        else if (a[oldX][oldY].indexOf("pao") >= 0) {
            if (Math.abs(stepY - oldY) != 0 && Math.abs(stepX - oldX) != 0)
                return false;
            if (stepX == oldX) {
                if (a[stepX][stepY] != null) {
                    var count = 0;
                    var min = Math.min(oldY, stepY), max = Math.max(oldY, stepY);
                    for (var i = min + 1; i <= max - 1; ++i)
                        if (a[stepX][i] != null) count++;
                    if (count != 1) return false;
                    return true;
                }
                else {
                    var count = 0;
                    var min = Math.min(oldY, stepY), max = Math.max(oldY, stepY);
                    for (var i = min + 1; i <= max - 1; ++i)
                        if (a[stepX][i] != null) count++;
                    if (count != 0) return false;
                    return true;
                }
            }
            else {
                if (a[stepX][stepY] != null) {
                    var count = 0;
                    var min = Math.min(oldX, stepX), max = Math.max(oldX, stepX);
                    for (var i = min + 1; i <= max - 1; ++i)
                        if (a[i][stepY] != null) count++;
                    if (count != 1) return false;
                    return true;
                }
                else {
                    var count = 0;
                    var min = Math.min(oldX, stepX), max = Math.max(oldX, stepX);
                    for (var i = min + 1; i <= max - 1; ++i)
                        if (a[i][stepY] != null) count++;
                    if (count != 0) return false;
                    return true;
                }
            }

        }
        else if (a[oldX][oldY].indexOf("ju") >= 0) {
            if (Math.abs(stepY - oldY) != 0 && Math.abs(stepX - oldX) != 0)
                return false;
            if (stepX == oldX) {
                var count = 0;
                var min = Math.min(stepY, oldY), max = Math.max(stepY, oldY);
                for (var i = min + 1; i <= max - 1; ++i)
                    if (a[stepX][i] != null) count++;
                if (count != 0) return false;
                return true;
            }
            else {
                var count = 0;
                var min = Math.min(stepX, oldX), max = Math.max(stepX, oldX);
                for (var i = min + 1; i <= max - 1; ++i)
                    if (a[i][stepY] != null) count++;
                if (count != 0) return false;
                return true;
            }
        }
        else if (a[oldX][oldY].indexOf("ma") >= 0) {
            if (Math.abs(stepX - oldX) == 2) {
                if (Math.abs(stepY - oldY) != 1) return false;
                if (stepX < oldX) {
                    if (a[oldX - 1][oldY] != null) return false;
                    return true;
                }
                else {
                    if (a[oldX + 1][oldY] != null) return false;
                    return true;
                }
            }
            else if (Math.abs(stepY - oldY) == 2) {
                if (Math.abs(stepX - oldX) != 1) return false;
                if (stepY < oldY) {
                    if (a[oldX][oldY-1] != null) return false;
                    return true;
                }
                else {
                    if (a[oldX][oldY+1] != null) return false;
                    return true;
                }
            }
            else{
                return false;
            }
        }
        else if(a[oldX][oldY].indexOf("xiang") >= 0){
            if(!(Math.abs(stepX-oldX)==2&&Math.abs(stepY-oldY)==2))
                return false;
            if(stepX<5) return false;
            var midx=(stepX+oldX)/2,midy=(stepY+oldY)/2;
            if(a[midx][midy]!=null) return false;
            return true;

        }
        else if(a[oldX][oldY].indexOf("shi") >= 0){
            if(!(Math.abs(stepX-oldX)==1&&Math.abs(stepY-oldY)==1))
                return false;
            if(stepX<7||stepY<3||stepY>5) return false;
            return true;
        }
        else if(a[oldX][oldY].indexOf("jiang") >= 0){
            if(Math.abs(stepX-oldX)+Math.abs(stepY-oldY)!=1)
                return false;
            if(stepX<7||stepY<3||stepY>5) return false;
            return true;
        }
    }
    else{
        if (a[oldX][oldY].indexOf("bing") >= 0) {
            if (stepX < oldX) return false;
            if (Math.abs(stepY - oldY) + Math.abs(stepX - oldX) != 1) return false;
            if (stepY != oldY) {
                if (Math.abs(stepY - oldY) != 1) return false;
                if (!(oldX >= 5 && oldX <= 9)) return false;
                return true;
            }
            return true;
        }
        else if (a[oldX][oldY].indexOf("pao") >= 0) {
            if (Math.abs(stepY - oldY) != 0 && Math.abs(stepX - oldX) != 0)
                return false;
            if (stepX == oldX) {
                if (a[stepX][stepY] != null) {
                    var count = 0;
                    var min = Math.min(oldY, stepY), max = Math.max(oldY, stepY);
                    for (var i = min + 1; i <= max - 1; ++i)
                        if (a[stepX][i] != null) count++;
                    if (count != 1) return false;
                    return true;
                }
                else {
                    var count = 0;
                    var min = Math.min(oldY, stepY), max = Math.max(oldY, stepY);
                    for (var i = min + 1; i <= max - 1; ++i)
                        if (a[stepX][i] != null) count++;
                    if (count != 0) return false;
                    return true;
                }
            }
            else {
                if (a[stepX][stepY] != null) {
                    var count = 0;
                    var min = Math.min(oldX, stepX), max = Math.max(oldX, stepX);
                    for (var i = min + 1; i <= max - 1; ++i)
                        if (a[i][stepY] != null) count++;
                    if (count != 1) return false;
                    return true;
                }
                else {
                    var count = 0;
                    var min = Math.min(oldX, stepX), max = Math.max(oldX, stepX);
                    for (var i = min + 1; i <= max - 1; ++i)
                        if (a[i][stepY] != null) count++;
                    if (count != 0) return false;
                    return true;
                }
            }

        }
        else if (a[oldX][oldY].indexOf("ju") >= 0) {
            if (Math.abs(stepY - oldY) != 0 && Math.abs(stepX - oldX) != 0)
                return false;
            if (stepX == oldX) {
                var count = 0;
                var min = Math.min(stepY, oldY), max = Math.max(stepY, oldY);
                for (var i = min + 1; i <= max - 1; ++i)
                    if (a[stepX][i] != null) count++;
                if (count != 0) return false;
                return true;
            }
            else {
                var count = 0;
                var min = Math.min(stepX, oldX), max = Math.max(stepX, oldX);
                for (var i = min + 1; i <= max - 1; ++i)
                    if (a[i][stepY] != null) count++;
                if (count != 0) return false;
                return true;
            }
        }
        else if (a[oldX][oldY].indexOf("ma") >= 0) {
            if (Math.abs(stepX - oldX) == 2) {
                if (Math.abs(stepY - oldY) != 1) return false;
                if (stepX < oldX) {
                    if (a[oldX - 1][oldY] != null) return false;
                    return true;
                }
                else {
                    if (a[oldX + 1][oldY] != null) return false;
                    return true;
                }
            }
            else if (Math.abs(stepY - oldY) == 2) {
                if (Math.abs(stepX - oldX) != 1) return false;
                if (stepY < oldY) {
                    if (a[oldX][oldY-1] != null) return false;
                    return true;
                }
                else {
                    if (a[oldX][oldY+1] != null) return false;
                    return true;
                }
            }
            else{
                return false;
            }
        }
        else if(a[oldX][oldY].indexOf("xiang") >= 0){
            if(!(Math.abs(stepX-oldX)==2&&Math.abs(stepY-oldY)==2))
                return false;
            if(stepX>4) return false;
            var midx=(stepX+oldX)/2,midy=(stepY+oldY)/2;
            if(a[midx][midy]!=null) return false;
            return true;

        }
        else if(a[oldX][oldY].indexOf("shi") >= 0){
            if(!(Math.abs(stepX-oldX)==1&&Math.abs(stepY-oldY)==1))
                return false;
            if(stepX>2||stepY<3||stepY>5) return false;
            return true;
        }
        else if(a[oldX][oldY].indexOf("jiang") >= 0){
            if(Math.abs(stepX-oldX)+Math.abs(stepY-oldY)!=1)
                return false;
            if(stepX>2||stepY<3||stepY>5) return false;
            return true;
        }
    }
    return true;

}